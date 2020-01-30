package com.williams.appraisalcompany.Controller;

import com.williams.appraisalcompany.Service.facade.AccountFacade;
import com.williams.appraisalcompany.model.Entity.User;
import com.williams.appraisalcompany.model.Request.UpsertLoanRequest;
import com.williams.appraisalcompany.model.Response.AppraisalApiResponse;
import com.williams.appraisalcompany.model.Response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/loan")
public class LoanController {

    private AccountFacade accountFacade;

    public LoanController(AccountFacade accountFacade) {
        Assert.notNull(accountFacade);
        this.accountFacade = accountFacade;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> createLoan(
            @RequestHeader("S-User-Token") String userToken,
            @RequestBody UpsertLoanRequest upsertLoanRequest) {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.createLoan(upsertLoanRequest, user);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET, path = "/{loanKey}",
            produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> getLoan(
            @RequestHeader("S-User-Token") String userToken,
            @PathVariable String loanKey) {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.getLoan(user, loanKey);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> getAllLoan(
            @RequestHeader("S-User-Token") String userToken) {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.getAllLoan(user);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.PUT,
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> updateLoan(
            @RequestHeader("S-User-Token") String userToken,
            @RequestBody UpsertLoanRequest upsertLoanRequest) {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.updateLoan(upsertLoanRequest, user);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
}
