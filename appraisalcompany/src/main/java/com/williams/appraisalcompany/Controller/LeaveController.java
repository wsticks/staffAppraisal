package com.williams.appraisalcompany.Controller;

import com.williams.appraisalcompany.Service.facade.AccountFacade;
import com.williams.appraisalcompany.model.Entity.User;
import com.williams.appraisalcompany.model.Request.UpsertLeaveRequest;
import com.williams.appraisalcompany.model.Response.AppraisalApiResponse;
import com.williams.appraisalcompany.model.Response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/leave")
public class LeaveController {

    private AccountFacade accountFacade;

    public LeaveController(AccountFacade accountFacade) {
        Assert.notNull(accountFacade);
        this.accountFacade = accountFacade;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> createLeave(
            @RequestHeader("S-User-Token") String userToken,
            @RequestBody UpsertLeaveRequest upsertLeaveRequest) {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.createLeave(upsertLeaveRequest, user);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET, path = "/{leaveKey}",
            produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> getLeave( @RequestHeader("S-User-Token") String userToken, @PathVariable String leaveKey) {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.getLeave(user, leaveKey);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }


    @Transactional
    @RequestMapping(method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> getAllLeave(@RequestHeader("S-User-Token") String userToken) {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.getAllLeave(user);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.PUT,
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> updateLeave(
            @RequestHeader("S-User-Token") String userToken,
            @RequestBody UpsertLeaveRequest upsertLeaveRequest) {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.updateLeave(upsertLeaveRequest, user);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
}
