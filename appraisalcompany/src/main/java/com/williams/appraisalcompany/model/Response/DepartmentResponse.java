package com.williams.appraisalcompany.model.Response;

import com.williams.appraisalcompany.Util.TimeUtil;
import com.williams.appraisalcompany.model.Entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentResponse {

    private String name;
    private String description;
    private String status;
    private String uniqueKey;
    private String createdAt;
    private String updatedAt;

    public static DepartmentResponse fromDepartment(Department department){
        return DepartmentResponse.builder()
                .createdAt(TimeUtil.getIsoTime(department.getCreatedAt()))
                .updatedAt(TimeUtil.getIsoTime(department.getUpdatedAt()))
                .uniqueKey(department.getUniqueKey())
                .status(String.valueOf(department.getStatus()))
                .description(department.getDescription())
                .name(department.getName())
                .build();
    }

    public static List<DepartmentResponse> fromDepartments(List<Department> departments) {
        return departments.stream().map(department -> {
            return fromDepartment(department);
        }).collect(Collectors.toList());
    }
}
