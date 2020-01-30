package com.williams.appraisalcompany.model.Entity;

import com.williams.appraisalcompany.model.Constant.LeaveType;
import com.williams.appraisalcompany.model.Constant.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "leave")
public class Leave implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Basic
    @Column(name = "unique_key", nullable = true)
    private String uniqueKey;
    @Basic
    @Column(name = "number_of_days", nullable = true)
    private String numberOfDays;
    @Basic
    @Column(name = "start_date", nullable = true)
    private String startDate;
    @Basic
    @Column(name = "end_date", nullable = true)
    private String endDate;
    @Basic
    @Column(name = "leave_type", nullable = true)
    private LeaveType leaveType;
    @Basic
    @Column(name = "employee_number", nullable = true)
    private String employeeNumber;
    @Basic
    @Column(name = "pending_leave_days", nullable = true)
    private String pendingLeaveDays;
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

    public String getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(String numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getPendingLeaveDays() {
        return pendingLeaveDays;
    }

    public void setPendingLeaveDays(String pendingLeaveDays) {
        this.pendingLeaveDays = pendingLeaveDays;
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
        Leave leave = (Leave) o;
        return Objects.equals(id, leave.id) &&
                Objects.equals(uniqueKey, leave.uniqueKey) &&
                Objects.equals(numberOfDays, leave.numberOfDays) &&
                Objects.equals(startDate, leave.startDate) &&
                Objects.equals(endDate, leave.endDate) &&
                leaveType == leave.leaveType &&
                Objects.equals(employeeNumber, leave.employeeNumber) &&
                Objects.equals(pendingLeaveDays, leave.pendingLeaveDays) &&
                status == leave.status &&
                Objects.equals(createdAt, leave.createdAt) &&
                Objects.equals(updatedAt, leave.updatedAt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, uniqueKey, numberOfDays, startDate, endDate, leaveType, employeeNumber, pendingLeaveDays, status, createdAt, updatedAt);
    }
}
