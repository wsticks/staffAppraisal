package com.williams.appraisalcompany.model.Request;

import com.williams.appraisalcompany.model.Constant.MaritalStatus;
import com.williams.appraisalcompany.model.Constant.Status;
import com.williams.appraisalcompany.model.Entity.Recruitment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor@Builder
public class UpsertStaffRequest {

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
    private String email;
    private String uniqueKey;
    private String department;
    private String salary;
    private String entitledLeaveDays;

    public Recruitment toStaff(){
        Recruitment recruitment = new Recruitment();
        recruitment.setAge(age);
        recruitment.setEmployeeNumber(employeeNumber);
        recruitment.setFirstName(firstName);
        recruitment.setLevel(level);
        recruitment.setMaritalStatus(MaritalStatus.valueOf(maritalStatus));
        recruitment.setMiddleName(middleName);
        recruitment.setNameOfSchool(nameOfSchool);
        recruitment.setSex(sex);
        recruitment.setDepartment(department);
        recruitment.setSurname(surname);
        recruitment.setStatus(Status.valueOf(status));
        recruitment.setEmail(email);
        recruitment.setSalary(salary);
        recruitment.setEntitledLeaveDays(entitledLeaveDays);
        return recruitment;
    }
}
