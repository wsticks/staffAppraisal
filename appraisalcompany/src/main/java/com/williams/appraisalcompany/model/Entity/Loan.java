package com.williams.appraisalcompany.model.Entity;

import com.williams.appraisalcompany.model.Constant.PaymentMethod;
import com.williams.appraisalcompany.model.Constant.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "loan")
public class Loan implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Basic
    @Column(name = "unique_key", nullable = true)
    private String uniqueKey;
    @Basic
    @Column(name = "employee", nullable = true)
    private String employee;
    @Basic
    @Column(name = "purpose", nullable = true)
    private String purpose;
    @Basic
    @Column(name = "amount", nullable = true)
    private String amount;
    @Basic
    @Column(name = "repayment_period", nullable = true)
    private String repaymentPeriod;
    @Basic
    @Column(name = "payment_method", nullable = true)
    private PaymentMethod paymentMethod;
    @Basic
    @Column(name = "payee_name", nullable = true)
    private String payeeName;
    @Basic
    @Column(name = "account_number", nullable = true)
    private BigDecimal accountNumber;
    @Basic
    @Column(name = "bank_sort_code", nullable = true)
    private String bankSortCode;
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

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRepaymentPeriod() {
        return repaymentPeriod;
    }

    public void setRepaymentPeriod(String repaymentPeriod) {
        this.repaymentPeriod = repaymentPeriod;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public BigDecimal getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(BigDecimal accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankSortCode() {
        return bankSortCode;
    }

    public void setBankSortCode(String bankSortCode) {
        this.bankSortCode = bankSortCode;
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
        Loan loan = (Loan) o;
        return Objects.equals(id, loan.id) &&
                Objects.equals(uniqueKey, loan.uniqueKey) &&
                Objects.equals(employee, loan.employee) &&
                Objects.equals(purpose, loan.purpose) &&
                Objects.equals(amount, loan.amount) &&
                Objects.equals(repaymentPeriod, loan.repaymentPeriod) &&
                paymentMethod == loan.paymentMethod &&
                Objects.equals(payeeName, loan.payeeName) &&
                Objects.equals(accountNumber, loan.accountNumber) &&
                Objects.equals(bankSortCode, loan.bankSortCode) &&
                status == loan.status &&
                Objects.equals(createdAt, loan.createdAt) &&
                Objects.equals(updatedAt, loan.updatedAt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, uniqueKey, employee, purpose, amount, repaymentPeriod, paymentMethod, payeeName, accountNumber, bankSortCode, status, createdAt, updatedAt);
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
