package com.williams.appraisalcompany.Service;

import com.williams.appraisalcompany.Repository.DepartmentRepository;
import com.williams.appraisalcompany.Util.SecurityUtil;
import com.williams.appraisalcompany.Util.TimeUtil;
import com.williams.appraisalcompany.exception.ProcessingException;
import com.williams.appraisalcompany.model.Entity.Department;
import com.williams.appraisalcompany.model.Request.UpsertDepartmentRequest;
import com.williams.appraisalcompany.model.Response.DepartmentResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DepartmentService {

    private DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        Assert.notNull(departmentRepository);
        this.departmentRepository = departmentRepository;
    }

    public DepartmentResponse prepareDepartmentForSave(UpsertDepartmentRequest upsertDepartmentRequest) {
        Department departmentToSave = upsertDepartmentRequest.toDepartment();
        generateUniqueKey(departmentToSave);
        Department savedDepartment = departmentRepository.save(departmentToSave);
        DepartmentResponse departmentResponse = new DepartmentResponse();
        departmentResponse.setCreatedAt(TimeUtil.getIsoTime(savedDepartment.getCreatedAt()));
        departmentResponse.setUpdatedAt(TimeUtil.getIsoTime(savedDepartment.getUpdatedAt()));
        departmentResponse.setDescription(savedDepartment.getDescription());
        departmentResponse.setName(savedDepartment.getName());
        departmentResponse.setUniqueKey(savedDepartment.getUniqueKey());
        departmentResponse.setStatus(String.valueOf(savedDepartment.getStatus()));
        return departmentResponse;
    }

    private void generateUniqueKey(Department department) throws ProcessingException {
        if (department.getUniqueKey() != null) {
            return;
        }
        String rawKey = department.getDescription() + LocalDateTime.now() + Math.random();
        String uniqueKey = SecurityUtil.hashWithMd5(rawKey);
        department.setUniqueKey(uniqueKey);
    }

    public List<Department> findAllDepartments() {
        return (List<Department>) departmentRepository.findAll();
    }

    public Department fetchDepartmentByUniqueKey(String uniqueKey){
        Department savedDepartment = departmentRepository.findOneByUniqueKey(uniqueKey);
        if(savedDepartment == null){
            throw new ProcessingException("Department not found");
        }
        return savedDepartment;
    }

    public Department prepareDepartmentForUpdate(Department departmentToSave, String uniqueKey){
        Department savedDepartment = fetchDepartmentByUniqueKey(uniqueKey);
        savedDepartment.setDescription(departmentToSave.getDescription());
        savedDepartment.setName(departmentToSave.getName());
        savedDepartment.setStatus(departmentToSave.getStatus());
        return departmentRepository.save(savedDepartment);
    }
}
