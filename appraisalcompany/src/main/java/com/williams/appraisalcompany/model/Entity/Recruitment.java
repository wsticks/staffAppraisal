package com.williams.appraisalcompany.model.Entity;

import com.williams.appraisalcompany.model.Constant.MaritalStatus;
import com.williams.appraisalcompany.model.Constant.Status;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "staff")
public class Recruitment {


    @Id
    @GeneratedValue
    private Long id;
    @Basic
    @Column(name = "unique_key", nullable = true)
    private String uniqueKey;
    @Basic
    @Column(name = "surname", nullable = true)
    private String surname;
    @Basic
    @Column(name = "first_name", nullable = true)
    private String firstName;
    @Basic
    @Column(name = "middle_name", nullable = true)
    private String middleName;
    @Basic
    @Column(name = "email", nullable = true)
    private String email;
    @Basic
    @Column(name = "sex", nullable = true)
    private String sex;
    @Basic
    @Column(name = "age", nullable = true)
    private String age;
    @Basic
    @Column(name = "department", nullable = true)
    private String department;
    @Basic
    @Column(name = "marital_status", nullable = true)
    private MaritalStatus maritalStatus;
    @Basic
    @Column(name = "employee_number", nullable = true)
    private String employeeNumber;
    @Basic
    @Column(name = "name_of_school", nullable = true)
    private String nameOfSchool;
    @Basic
    @Column(name = "level", nullable = true)
    private String level;
    @Basic
    @Column(name = "salary", nullable = true)
    private String salary;
    @Basic
    @Column(name = "entitled_leave_days", nullable = true)
    private String entitledLeaveDays;
    @Basic
    @Column(name = "status", nullable = true)
    private Status status;
    @Basic
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
    @Basic
    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getNameOfSchool() {
        return nameOfSchool;
    }

    public void setNameOfSchool(String nameOfSchool) {
        this.nameOfSchool = nameOfSchool;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getEntitledLeaveDays() {
        return entitledLeaveDays;
    }

    public void setEntitledLeaveDays(String entitledLeaveDays) {
        this.entitledLeaveDays = entitledLeaveDays;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recruitment that = (Recruitment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(uniqueKey, that.uniqueKey) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(middleName, that.middleName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(sex, that.sex) &&
                Objects.equals(age, that.age) &&
                Objects.equals(department, that.department) &&
                maritalStatus == that.maritalStatus &&
                Objects.equals(employeeNumber, that.employeeNumber) &&
                Objects.equals(nameOfSchool, that.nameOfSchool) &&
                Objects.equals(level, that.level) &&
                Objects.equals(salary, that.salary) &&
                Objects.equals(entitledLeaveDays, that.entitledLeaveDays) &&
                status == that.status &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, uniqueKey, surname, firstName, middleName, email, sex, age, department, maritalStatus, employeeNumber, nameOfSchool, level, salary, entitledLeaveDays, status, createdAt, updatedAt);
    }

    @PrePersist
    public void beforeSave() {
        this.createdAt = new Timestamp(new Date().getTime());
    }

    @PreUpdate
    private void beforeUpdate() {
        this.updatedAt = new Timestamp(new Date().getTime());
    }
}
