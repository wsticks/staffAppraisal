package com.williams.appraisalcompany.model.Response;

import com.williams.appraisalcompany.Util.TimeUtil;
import com.williams.appraisalcompany.model.Constant.PaymentMethod;
import com.williams.appraisalcompany.model.Constant.Status;
import com.williams.appraisalcompany.model.Entity.Loan;
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
public class LoanResponse {

    private String uniqueKey;
    private String employee;
    private String purpose;
    private String amount;
    private String repaymentPeriod;
    private PaymentMethod paymentMethod;
    private String payeeName;
    private String accountNumber;
    private String bankSortCode;
    private Status status;
    private String createdAt;
    private String updatedAt;

    public static  LoanResponse fromLoan(Loan loan){
        return LoanResponse.builder()
                .createdAt(TimeUtil.getIsoTime(loan.getCreatedAt()))
                .updatedAt(TimeUtil.getIsoTime(loan.getUpdatedAt()))
                .status(loan.getStatus())
                .uniqueKey(loan.getUniqueKey())
                .employee(loan.getEmployee())
                .purpose(loan.getPurpose())
                .amount(loan.getAmount())
                .repaymentPeriod(loan.getRepaymentPeriod())
                .payeeName(loan.getPayeeName())
                .paymentMethod(loan.getPaymentMethod())
                .payeeName(loan.getPayeeName())
                .accountNumber(String.valueOf(loan.getAccountNumber()))
                .bankSortCode(loan.getBankSortCode())
                .build();
    }

    public static List<LoanResponse> fromLoans(List<Loan> loans){
        return loans.stream().map(loan -> {
            return fromLoan(loan); })
                .collect(Collectors.toList());
    }
}
