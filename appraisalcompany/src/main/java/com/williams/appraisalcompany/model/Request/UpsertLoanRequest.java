package com.williams.appraisalcompany.model.Request;

import com.williams.appraisalcompany.model.Constant.PaymentMethod;
import com.williams.appraisalcompany.model.Constant.Status;
import com.williams.appraisalcompany.model.Entity.Loan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpsertLoanRequest {

    private String employeeUniquekey;
    private String purpose;
    private String amount;
    private String repaymentPeriod;
    private PaymentMethod paymentMethod;
    private String payeeName;
    private BigDecimal accountNumber;
    private String bankSortCode;
    private Status status;
    private String uniqueKey;

    public Loan toLoan(){
        Loan loan = new Loan();
        loan.setStatus(status);
        if(status == null || status.equals("")){
            loan.setStatus(Status.NEW);
        }
        loan.setEmployee(employeeUniquekey);
        loan.setAmount(amount);
        loan.setRepaymentPeriod(repaymentPeriod);
        loan.setPaymentMethod(paymentMethod);
        loan.setPayeeName(payeeName);
        loan.setAccountNumber(accountNumber);
        loan.setBankSortCode(bankSortCode);
        loan.setPurpose(purpose);
        return loan;
    }
}
