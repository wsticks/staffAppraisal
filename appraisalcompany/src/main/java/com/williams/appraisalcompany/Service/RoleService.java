package com.williams.appraisalcompany.Service;

import com.williams.appraisalcompany.Repository.PermissionRepository;
import com.williams.appraisalcompany.Repository.RolePermissionRepository;
import com.williams.appraisalcompany.Repository.RoleRepository;
import com.williams.appraisalcompany.Util.GatewayBeanUtil;
import com.williams.appraisalcompany.Util.SecurityUtil;
import com.williams.appraisalcompany.exception.AppraisalApiException;
import com.williams.appraisalcompany.exception.NotFoundException;
import com.williams.appraisalcompany.exception.ProcessingException;
import com.williams.appraisalcompany.model.Constant.Status;
import com.williams.appraisalcompany.model.Entity.Permission;
import com.williams.appraisalcompany.model.Entity.Role;
import com.williams.appraisalcompany.model.Entity.RolePermission;
import com.williams.appraisalcompany.model.Entity.User;
import com.williams.appraisalcompany.model.Request.UpsertRoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    private static final String ROLE_NOT_FOUND = "Role not found";
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private  final RolePermissionRepository rolePermissionRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository,
                       PermissionRepository permissionRepository,
                       RolePermissionRepository rolePermissionRepository) {
        Assert.notNull(roleRepository);
        Assert.notNull(permissionRepository);
        Assert.notNull(rolePermissionRepository);
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    public ArrayList getPermissionCodesForRole(Integer roleId) throws AppraisalApiException {
        List<RolePermission> rolePermissions = rolePermissionRepository.findAllByRoleId(roleId);
        List<Integer> permissionIds = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissions) {
            permissionIds.add(rolePermission.getPermssionId());
        }
        ArrayList permissionCodes = new ArrayList<>();
        List<Permission> permissions = permissionRepository.findAllByIdIn(permissionIds);
        for (Permission permission : permissions) {
            permissionCodes.add(permission.getCode());
        }
        return permissionCodes;
    }

    //Creating role
    public Role createRole(UpsertRoleRequest request, User user)
            throws AppraisalApiException {
        Role role = request.toRole(user.getUniqueKey());
        prepareRoleForCreation(role);
        role = roleRepository.save(role);
        addPermissions(request, role);
        return role;
    }

    private void prepareRoleForCreation(Role role) throws AppraisalApiException {
        generateUniqueKey(role);
    }

    private void generateUniqueKey(Role role) throws ProcessingException {
        if (role.getUniqueKey() != null) {
            return;
        }
        String rawKey = role.getName() + LocalDateTime.now() + Math.random();
        String uniqueKey = SecurityUtil.hashWithMd5(rawKey);
        role.setUniqueKey(uniqueKey);
    }

    private void addPermissions(UpsertRoleRequest request, Role role)
            throws AppraisalApiException {
        List<Integer> permissionIds = getValidPermissionIds(request.getPermissionIds());
        for (Integer permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermssionId(permissionId);
            rolePermission.setStatus(Status.ACTIVE);
            rolePermissionRepository.save(rolePermission);
        }
    }


    private List<Integer> getValidPermissionIds(List<Integer> ids) {
        List<Permission> validPermissions = permissionRepository.findAllByIdIn(ids);
        List<Integer> validIds = new ArrayList<>();
        for (Permission permission : validPermissions) {
            validIds.add(permission.getId());
        }
        return validIds;
    }
    //End of creating role





    //Get by unique key
    public Role fetchByUniqueKey(String uniqueKey) {
        Role role = roleRepository.findOneByUniqueKey(uniqueKey);
        if (role == null) {
            throw new NotFoundException(ROLE_NOT_FOUND);
        }
        return role;
    }//End of fetching by unique key

    // Get Role
    public List<Role> getRoles(){return roleRepository.findAll();}
    public List<Role> getMerchantRoles() { return roleRepository.findAllByIsHidden(Status.INACTIVE); }
    public Role getRole(String uniqueKey) {
        return roleRepository.findOneByUniqueKey(uniqueKey);
    }

    // fetch role by Id
    public Role fetchRoleById(Integer roleId) {
        Role role = roleRepository.findOneById(roleId);
        if (role == null) {
            throw new NotFoundException(ROLE_NOT_FOUND);
        }
        return role;
    }// End of fetch role by Id

    public List<Integer> getRolePermissionIds(Integer roleId) {
        List<RolePermission> rolePermissions = rolePermissionRepository
                .findPermissionIdsByRoleId(roleId);
        List<Integer> permissionIds = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissions) {
            permissionIds.add(rolePermission.getPermssionId());
        }
        List<Permission> permissions = permissionRepository.findAllByIdIn(permissionIds);
        return permissionIds;
    }


    public Role updateRole(Role roleToUpdate, UpsertRoleRequest request,
                           User authenticatedUser) {
        rolePermissionRepository.deleteAllByRoleId(roleToUpdate.getId());
        GatewayBeanUtil
                .copyProperties(request.toRole(authenticatedUser.getUniqueKey()), roleToUpdate);
        addPermissions(request, roleToUpdate);
        return roleRepository.save(roleToUpdate);
    }

}