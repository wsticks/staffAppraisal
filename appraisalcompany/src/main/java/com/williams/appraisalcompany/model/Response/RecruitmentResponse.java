package com.williams.appraisalcompany.model.Response;

import com.williams.appraisalcompany.Util.TimeUtil;
import com.williams.appraisalcompany.model.Entity.Recruitment;
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
public class RecruitmentResponse {

    private String surname;
    private String firstName;
    private String middleName;
    private String sex;
    private String age;
    private String maritalStatus;
    private String employeeNumber;
    private String nameOfSchool;
    private String level;
    private String status;
    private String createdAt;
    private String updatedAt;
    private String uniqueKey;
    private String email;
    private String department;
    private String entitleNumberOfDays;

    public static RecruitmentResponse fromStaffUpdate(Recruitment recruitment){
        return RecruitmentResponse.builder()
                .surname(recruitment.getSurname())
                .firstName(recruitment.getFirstName())
                .middleName(recruitment.getMiddleName())
                .sex(recruitment.getSex())
                .age(recruitment.getAge())
                .maritalStatus(String.valueOf(recruitment.getMaritalStatus()))
                .employeeNumber(recruitment.getEmployeeNumber())
                .nameOfSchool(recruitment.getNameOfSchool())
                .level(recruitment.getLevel())
                .status(String.valueOf(recruitment.getStatus()))
                .createdAt(TimeUtil.getIsoTime(recruitment.getCreatedAt()))
                .updatedAt(TimeUtil.getIsoTime(recruitment.getUpdatedAt()))
                .uniqueKey(recruitment.getUniqueKey())
                .email(recruitment.getEmail())
                .department(recruitment.getDepartment())
                .entitleNumberOfDays(recruitment.getEntitledLeaveDays())
                .build();
    }

    public static List<RecruitmentResponse> fromStaff(List<Recruitment> staffs){
        return staffs.stream().map(staff->{
            return fromStaffUpdate(staff);
        }).collect(Collectors.toList());
    }
}
