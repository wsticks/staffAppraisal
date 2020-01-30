package com.williams.appraisalcompany.Service.facade;


import com.williams.appraisalcompany.Service.*;
import com.williams.appraisalcompany.Util.TimeUtil;
import com.williams.appraisalcompany.exception.ProcessingException;
import com.williams.appraisalcompany.model.Entity.*;
import com.williams.appraisalcompany.model.Request.*;
import com.williams.appraisalcompany.model.Response.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

@Component
public class AccountFacade extends  RequestFacade {

    private final RoleService roleService;
    private final UserService userService;
    private final RecruitmentService recruitmentService;
    private final DepartmentService departmentService;
    private final LeaveService leaveService;
    private final LoanService loanService;
    private final PermissionHandler permissionHandler;
    private static final Integer USER_ROLE_ID = 2;
    private static final Integer GLOBAL_ADMIN_ROLE_ID = 1;

    public AccountFacade(RoleService roleService,
                         UserService userService,
                         RecruitmentService recruitmentService,
                         DepartmentService departmentService,
                         LeaveService leaveService,
                         LoanService loanService,
                         PermissionHandler permissionHandler) {
        Assert.notNull(permissionHandler);
        Assert.notNull(userService);
        Assert.notNull(roleService);
        Assert.notNull(recruitmentService);
        Assert.notNull(departmentService);
        Assert.notNull(leaveService);
        this.roleService = roleService;
        this.userService = userService;
        this.permissionHandler = permissionHandler;
        this.recruitmentService = recruitmentService;
        this.departmentService = departmentService;
        this.loanService = loanService;
        this.leaveService = leaveService;
    }

    public SuccessResponse createUser(UpsertUserRequest upsertUserRequest) {
        upsertUserRequest.setRoleId(fetchRoleId(upsertUserRequest));
        User user = userService.createUser(upsertUserRequest.toUser());
        return buildSuccessResponse(createUserResponseData(user));
    }

    public SuccessResponse updateUser(
            UpsertUserRequest upsertUserRequest, String userKey, String userToken) {
        User actor = userService.getAuthenticatedUser(userToken);
        User userToUpdate = userService.fetchByUniqueKey(userKey);
        User updateUser = upsertUserRequest.toUser();
        if (!actor.getUniqueKey().equals(userKey)) {
            ArrayList permissions =
                    roleService.getPermissionCodesForRole(roleService.getRole(actor.getRoleId()).getId());
            permissionHandler.hasPermission(permissions, "user_update");
        }
        User savedUser = userService.updateUser(userToUpdate, updateUser, actor);
        return buildSuccessResponse(createUserResponseData(savedUser));
    }

    public User getAuthenticatedUser(String userToken) {
        return userService.getAuthenticatedUser(userToken);
    }

    public User fetchUserByUniqueKey(String userKey) {
        return userService.fetchByUniqueKey(userKey);
    }

    public SuccessResponse getUserInfo(User user, User actor) {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(roleService.getRole(actor.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "user_view");
        Map<String, Object> data = createUserResponseData(user);
        return buildSuccessResponse(data);
    }

    public SuccessResponse authenticateUser(AuthenticateUserRequest authenticateUserRequest) {
        User userToAuthenticate = authenticateUserRequest.toUser();
        User authenticatedUser = userService.authenticateUser(userToAuthenticate);
        Role userRole = roleService.getRole(authenticatedUser.getRoleId());
        ArrayList permissions = roleService.getPermissionCodesForRole(userRole.getId());
        Token token = userService.createToken(authenticatedUser);
        Map<String, Object> data = createUserResponseData(authenticatedUser);
        data.put("permission", permissions);
        data.put("auth", AuthResponse.fromToken(token));
        return buildSuccessResponse(data);
    }

    public SuccessResponse resetPassword(UpsertUserRequest upsertUserRequest) {
        User user = userService.fetchByEmail(upsertUserRequest.getEmail());
        user = changePassword(upsertUserRequest, user);
        return buildSuccessResponse(createUserResponseData(user));
    }

    public SuccessResponse changeUserPassword(UpsertUserRequest upsertUserRequest, User user) {
        user = changePassword(upsertUserRequest, user);
        return buildSuccessResponse(createUserResponseData(user));
    }

    public SuccessResponse logoutUser(User user, String token) {
        userService.logout(token);
        user.setLastLoginDate(TimeUtil.now());
        return buildSuccessResponse(createUserResponseData(user));
    }

    public SuccessResponse confirmUserEmail(ConfirmEmailRequest request) {
        User user = userService.fetchByEmail(request.getEmail());
        user = userService.confirmEmail(user);
        return buildSuccessResponse(createUserResponseData(user));
    }

    public SuccessResponse getAllUsers(User authenticatedUser, UserSearchRequest request) {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(
                        roleService.getRole(authenticatedUser.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "user_index");
        if (!userService.isCurrentGlobal(authenticatedUser.getUniqueKey())) {
            request.setUniqueKey(authenticatedUser.getUniqueKey());
            return createPaymentPaginatedResponse(userService.findAllUsers(request));
        }
        Page<User> userPage = userService.findAllUsers(request);
        return createPaymentPaginatedResponse(userPage);
    } //End of create User!


    //Beginning of Staff Creation
//    public SuccessResponse createStaff( UpsertStaffRequest upsertStaffRequest, User authenticatedUser) {
//        ArrayList permissions =
//                roleService.getPermissionCodesForRole(
//                        roleService.getRole(authenticatedUser.getRoleId()).getId());
//        permissionHandler.hasPermission(permissions, "user_create");
//        RecruitmentResponse recruitmentResponse = recruitmentService.prepareStaffForSave(upsertStaffRequest);
//        Map<String, Object> data = new HashMap<>();
//        data.put("staff", recruitmentResponse);
//        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
//        return successResponse;
//    }
//
//    public SuccessResponse updateStaff(UpsertStaffRequest upsertStaffRequest, User authenticatedUser) {
//        ArrayList permissions =
//                roleService.getPermissionCodesForRole(
//                        roleService.getRole(authenticatedUser.getRoleId()).getId());
//        permissionHandler.hasPermission(permissions, "user_update");
//        Recruitment recruitment = recruitmentService
//                .prepareStaffForUpdate(upsertStaffRequest.toStaff(),upsertStaffRequest.getUniqueKey());
//        RecruitmentResponse recruitmentResponse = RecruitmentResponse.fromStaffUpdate(recruitment);
//        Map<String, Object> data = new HashMap<>();
//        data.put("staff", recruitmentResponse);
//        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
//        return successResponse;
//    }

//    public SuccessResponse getStaff(User authenticatedUser, String uniqueKey) {
//        ArrayList permissions =
//                roleService.getPermissionCodesForRole(
//                        roleService.getRole(authenticatedUser.getRoleId()).getId());
//        permissionHandler.hasPermission(permissions, "user_view");
//        Recruitment recruitment = recruitmentService.fetchStaff(uniqueKey);
//        List<Leave> leaves = leaveService.fetchLeaveByEmployeeUniqueKey(recruitment.getUniqueKey());
//        List<Loan> loans = loanService.fetchLoanByEmployeeUniqueKey(recruitment.getUniqueKey());
//        RecruitmentResponse recruitmentResponse = RecruitmentResponse.fromStaffUpdate(recruitment);
//        List<LeaveResponse> leaveResponses = LeaveResponse.fromLeaveUpdates(leaves);
//        List<LoanResponse> loanResponses = LoanResponse.fromLoans(loans);
//        Map<String, Object> data = new HashMap<>();
//        data.put("staff", recruitmentResponse);
//        data.put("leaves", leaveResponses);
//        data.put("loans", loanResponses);
//        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
//        return successResponse;
//    }
//
//    public SuccessResponse getAllStaff(User authenticatedUser, StaffSearchRequest request) {
//        ArrayList permissions =
//                roleService.getPermissionCodesForRole(
//                        roleService.getRole(authenticatedUser.getRoleId()).getId());
//        permissionHandler.hasPermission(permissions, "user_index");
//        Page<Recruitment> recruitment = recruitmentService.findAllStaff(request);
//        return createStaffPaginatedResponse(recruitment);
//    }

    private String fetchRoleId(UpsertUserRequest upsertUserRequest){
//        if(upsertUserRequest.getRoleId()=="SHADMIN"){
//            upsertUserRequest.setRoleId(roleService.fetchRoleById(GLOBAL_ADMIN_ROLE_ID).getUniqueKey());
//        }
        if(upsertUserRequest.getRoleId()==null ){
            upsertUserRequest.setRoleId(roleService.fetchRoleById(USER_ROLE_ID).getUniqueKey());
        }
        return upsertUserRequest.getRoleId();
    }

    private User changePassword(UpsertUserRequest upsertUserRequest, User user) {
        User updateUser = upsertUserRequest.toUser();
        updateUser.setPassword(upsertUserRequest.getPassword());
        user = userService.resetPassword(user, updateUser);
        return user;
    }

    private SuccessResponse createPaymentPaginatedResponse(Page<User> paymentsPage) {
        String contentName = "users";
        Page<UserResponse> userResponses = paymentsPage.map(UserResponse::fromUser);
        userResponses.forEach(
                userResponse -> {
                    userResponse.setRoleName(roleService.getRole(userResponse.getRoleId()).getName());
                });
        return buildPaginatedSuccessResponse(contentName, userResponses);
    }
//
//    private SuccessResponse createStaffPaginatedResponse(Page<Recruitment> staffPage) {
//        String contentName = "staff";
//        Page<RecruitmentResponse> rcruitmentResponses = staffPage
//                .map(RecruitmentResponse::fromStaffUpdate);
//        return buildPaginatedSuccessResponse(contentName, rcruitmentResponses);
//    }

    private Map<String, Object> createUserResponseData(User user) {
        UserResponse userResponse = UserResponse.fromUser(user);
        Map<String, Object> data = new HashMap<>(1);
        data.put("user", userResponse);
        return data;
    }

    //operation on staff
   public SuccessResponse createStaff(UpsertStaffRequest upsertStaffRequest, User authenticatedUser){
        ArrayList permissions =
                roleService.getPermissionCodesForRole(
                        roleService.getRole(authenticatedUser.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "user_create");
       RecruitmentResponse recruitmentResponse = recruitmentService.prepareStaffForSave(upsertStaffRequest);
       Map<String,Object > data = new HashMap<>();
       data.put("staff", recruitmentResponse);
       SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
       return successResponse;
   }

   public SuccessResponse getStaff(User authenticatedUser, String uniqueKey){
        ArrayList permissions = roleService.getPermissionCodesForRole(
                roleService.getRole(authenticatedUser.getRoleId()).getId());
                permissionHandler.hasPermission(permissions, "user_view");
                Recruitment recruitment = recruitmentService.fetchStaff(uniqueKey);
       List<Leave> leaves = leaveService.fetchLeaveByEmployeeUniqueKey(recruitment.getUniqueKey());
       List<Loan> loans = loanService.fetchLoanByEmployeeUniqueKey(recruitment.getUniqueKey());
       RecruitmentResponse recruitmentResponse = RecruitmentResponse.fromStaffUpdate(recruitment);
       List<LeaveResponse> leaveResponses = LeaveResponse.fromLeaveUpdates(leaves);
       List<LoanResponse> loanResponses = LoanResponse.fromLoans(loans);
       Map<String, Object> data = new HashMap<>();
       data.put("staff", recruitmentResponse);
       data.put("leaves", leaveResponses);
       data.put("loans", loanResponses);
       SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
       return successResponse;
   }

   public SuccessResponse getAllStaff(User authenticatedUser, StaffSearchRequest request){
        ArrayList permissions=
                roleService.getPermissionCodesForRole(
                        roleService.getRole(authenticatedUser.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "user_index");
        Page<Recruitment> recruitment = recruitmentService.findAllStaff(request);
        return createStaffPaginatedResponse(recruitment);
   }

    private SuccessResponse createStaffPaginatedResponse(Page<Recruitment> staffPage) {
        String contentName = "staff";
        Page<RecruitmentResponse> rcruitmentResponses = staffPage
                .map(RecruitmentResponse::fromStaffUpdate);
        return buildPaginatedSuccessResponse(contentName, rcruitmentResponses);
    }

    public SuccessResponse updateStaff(UpsertStaffRequest upsertStaffRequest, User authenticatedUser) {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(
                        roleService.getRole(authenticatedUser.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "user_update");
        Recruitment recruitment = recruitmentService
                .prepareStaffForUpdate(upsertStaffRequest.toStaff(),upsertStaffRequest.getUniqueKey());
        RecruitmentResponse recruitmentResponse = RecruitmentResponse.fromStaffUpdate(recruitment);
        Map<String, Object> data = new HashMap<>();
        data.put("staff", recruitmentResponse);
        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
        return successResponse;
    }//End of operation

    public SuccessResponse createDepartment (UpsertDepartmentRequest upsertDepartmentRequest, User authenticateUser){
        ArrayList permissions =
                roleService.getPermissionCodesForRole(
                        roleService.getRole(authenticateUser.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "user_create");
        DepartmentResponse departmentResponse = departmentService.prepareDepartmentForSave(upsertDepartmentRequest);
        Map<String, Object> data = new HashMap<>();
        data.put("departments", departmentResponse);
        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
        return successResponse;
    }

    public SuccessResponse getDepartment(User authenticatedUser, String uniqueKey) {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(
                        roleService.getRole(authenticatedUser.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "user_view");
        Department department = departmentService.fetchDepartmentByUniqueKey(uniqueKey);
        DepartmentResponse departmentResponse = DepartmentResponse.fromDepartment(department);
        Map<String, Object> data = new HashMap<>();
        data.put("departments", departmentResponse);
        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
        return successResponse;
    }

    public SuccessResponse getAllDepartments(User authenticatedUser) {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(
                        roleService.getRole(authenticatedUser.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "user_index");
        List<Department> department = departmentService.findAllDepartments();
        List<DepartmentResponse> departmentResponse = DepartmentResponse.fromDepartments(department);
        Map<String, Object> data = new HashMap<>();
        data.put("departments", departmentResponse);
        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
        return successResponse;
    }

    public SuccessResponse updateDepartment( UpsertDepartmentRequest upsertDepartmentRequest, User authenticatedUser) {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(
                        roleService.getRole(authenticatedUser.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "user_update");
        Department department = departmentService
                .prepareDepartmentForUpdate(upsertDepartmentRequest.toDepartment(),upsertDepartmentRequest.getUniqueKey());
        DepartmentResponse departmentResponse = DepartmentResponse.fromDepartment(department);
        Map<String, Object> data = new HashMap<>();
        data.put("departments", departmentResponse);
        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
        return successResponse;
    }

    public SuccessResponse createLeave( UpsertLeaveRequest upsertLeaveRequest, User authenticatedUser) {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(
                        roleService.getRole(authenticatedUser.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "user_create");
        Recruitment recruitment = recruitmentService.fetchStaff(upsertLeaveRequest.getEmployeeUniqueKey());
        Leave leave = leaveService.prepareLeaveForSave(upsertLeaveRequest, recruitment);
        LeaveResponse leaveResponse = LeaveResponse.fromLeaveUpdate(leave);
        Map<String, Object> data = new HashMap<>();
        data.put("leaves", leaveResponse);
        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
        return successResponse;
    }

    public SuccessResponse getLeave(User authenticatedUser, String uniqueKey) {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(
                        roleService.getRole(authenticatedUser.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "user_view");
        Leave leave = leaveService.fetchLeaveByUniqueKey(uniqueKey);
        LeaveResponse leaveResponse = LeaveResponse.fromLeaveUpdate(leave);
        Map<String, Object> data = new HashMap<>();
        data.put("leaves", leaveResponse);
        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
        return successResponse;
    }

    public SuccessResponse getAllLeave(User authenticatedUser) {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(
                        roleService.getRole(authenticatedUser.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "user_index");
        List<Leave> leaves = leaveService.findAllLeaves();
        List<LeaveResponse> leaveResponses = LeaveResponse.fromLeaveUpdates(leaves);
        Map<String, Object> data = new HashMap<>();
        data.put("leaves", leaveResponses);
        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
        return successResponse;
    }

    public SuccessResponse updateLeave( UpsertLeaveRequest upsertLeaveRequest, User authenticatedUser) {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(
                        roleService.getRole(authenticatedUser.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "user_update");
        Leave leave = leaveService
                .prepareLeaveForUpdate(upsertLeaveRequest.toLeave(), upsertLeaveRequest.getUniqueKey());
        LeaveResponse leaveResponse = LeaveResponse.fromLeaveUpdate(leave);
        Map<String, Object> data = new HashMap<>();
        data.put("leaves", leaveResponse);
        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
        return successResponse;
    }

    public SuccessResponse createLoan( UpsertLoanRequest upsertLoanRequest, User authenticatedUser) {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(
                        roleService.getRole(authenticatedUser.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "user_create");
        Recruitment recruitment = recruitmentService.fetchStaff(upsertLoanRequest.getEmployeeUniquekey());
        if(Integer.parseInt(recruitment.getSalary()) <= Integer.parseInt(upsertLoanRequest.getAmount())){
            throw new ProcessingException("Loan amount cannot be higher or equal to monthly pay");
        }
        LoanResponse loanResponse = loanService.prepareLoanForSave(upsertLoanRequest);
        Map<String, Object> data = new HashMap<>();
        data.put("loans", loanResponse);
        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
        return successResponse;
    }

    public SuccessResponse getLoan(User authenticatedUser, String uniqueKey) {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(
                        roleService.getRole(authenticatedUser.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "user_view");
        Loan loan = loanService.fetchLoanByUniqueKey(uniqueKey);
        LoanResponse loanResponse = LoanResponse.fromLoan(loan);
        Map<String, Object> data = new HashMap<>();
        data.put("loans", loanResponse);
        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
        return successResponse;
    }

    public SuccessResponse getAllLoan(User authenticatedUser) {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(
                        roleService.getRole(authenticatedUser.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "user_index");
        List<Loan> loan = loanService.findAllLoans();
        List<LoanResponse> loanResponses = LoanResponse.fromLoans(loan);
        Map<String, Object> data = new HashMap<>();
        data.put("loans", loanResponses);
        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
        return successResponse;
    }

    public SuccessResponse updateLoan( UpsertLoanRequest upsertLoanRequest, User authenticatedUser) {
        ArrayList permissions =
                roleService.getPermissionCodesForRole(
                        roleService.getRole(authenticatedUser.getRoleId()).getId());
        permissionHandler.hasPermission(permissions, "user_update");
        Loan loan = loanService
                .prepareLoanForUpdate(upsertLoanRequest.toLoan(), upsertLoanRequest.getUniqueKey());
        LoanResponse loanResponse = LoanResponse.fromLoan(loan);
        Map<String, Object> data = new HashMap<>();
        data.put("loans", loanResponse);
        SuccessResponse successResponse = SuccessResponse.builder().data(data).build();
        return successResponse;
    }
}
