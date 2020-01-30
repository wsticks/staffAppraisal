package com.williams.appraisalcompany.Service;

import com.williams.appraisalcompany.Repository.LoanRepository;
import com.williams.appraisalcompany.Util.SecurityUtil;
import com.williams.appraisalcompany.Util.TimeUtil;
import com.williams.appraisalcompany.exception.ProcessingException;
import com.williams.appraisalcompany.model.Entity.Loan;
import com.williams.appraisalcompany.model.Request.UpsertLoanRequest;
import com.williams.appraisalcompany.model.Response.LoanResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanService {

    private LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        Assert.notNull(loanRepository);
        this.loanRepository = loanRepository;
    }

    public LoanResponse prepareLoanForSave(UpsertLoanRequest upsertLoanRequest){
        Loan loanToSave =  upsertLoanRequest.toLoan();
        generateUniqueKey(loanToSave);
        Loan savedLoan =  loanRepository.save(loanToSave);
        String accountNumber = "00" + savedLoan.getAccountNumber();
        LoanResponse loanResponse = new LoanResponse();
        loanResponse.setAmount(savedLoan.getAmount());
        loanResponse.setCreatedAt(TimeUtil.getIsoTime(savedLoan.getCreatedAt()));
        loanResponse.setAccountNumber(accountNumber);
        loanResponse.setStatus(savedLoan.getStatus());
        loanResponse.setUniqueKey(savedLoan.getUniqueKey());
        loanResponse.setUpdatedAt(TimeUtil.getIsoTime(savedLoan.getUpdatedAt()));
        loanResponse.setBankSortCode(savedLoan.getBankSortCode());
        loanResponse.setEmployee(savedLoan.getEmployee());
        loanResponse.setPayeeName(savedLoan.getPayeeName());
        loanResponse.setPaymentMethod(savedLoan.getPaymentMethod());
        loanResponse.setPurpose(savedLoan.getPurpose());
        loanResponse.setRepaymentPeriod(savedLoan.getRepaymentPeriod());
        return loanResponse;
    }

    public List<Loan> fetchLoanByEmployeeUniqueKey(String uniqueKey){
        List<Loan> savedLoan = loanRepository.findAllByEmployee(uniqueKey);
        return savedLoan;
    }

    private void generateUniqueKey(Loan loan) throws ProcessingException {
        if (loan.getUniqueKey() != null) {
            return;
        }
        String rawKey = loan.getEmployee() + LocalDateTime.now() + Math.random();
        String uniqueKey = SecurityUtil.hashWithMd5(rawKey);
        loan.setUniqueKey(uniqueKey);
    }

    public Loan fetchLoanByUniqueKey(String uniqueKey){
        Loan savedLoan = loanRepository.findOneByUniqueKey(uniqueKey);
        if(savedLoan == null){
            throw new ProcessingException("Loan not found");
        }
        return savedLoan;
    }

    public List<Loan> findAllLoans() {
        return (List<Loan>) loanRepository.findAll();
    }

    public Loan  prepareLoanForUpdate( Loan loanToSave, String uniqueKey){
        Loan savedLoan = fetchLoanByUniqueKey(uniqueKey);
        savedLoan.setStatus(loanToSave.getStatus());
        return loanRepository.save(savedLoan);
    }
}
