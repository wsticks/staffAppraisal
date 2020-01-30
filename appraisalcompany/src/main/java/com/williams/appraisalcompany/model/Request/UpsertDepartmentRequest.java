package com.williams.appraisalcompany.model.Request;

import com.williams.appraisalcompany.model.Constant.Status;
import com.williams.appraisalcompany.model.Entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpsertDepartmentRequest {

    private String name;
    private String description;
    private String status;
    private String uniqueKey;

    public Department toDepartment(){
        Department department = new Department();
        department.setName(name);
        department.setDescription(description);
        department.setStatus(Status.valueOf(status));
        return department;
    }
}
