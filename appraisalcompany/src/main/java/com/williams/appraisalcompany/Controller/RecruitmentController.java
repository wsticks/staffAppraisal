package com.williams.appraisalcompany.Controller;

import com.williams.appraisalcompany.Service.facade.AccountFacade;
import com.williams.appraisalcompany.model.Entity.User;
import com.williams.appraisalcompany.model.Request.StaffSearchRequest;
import com.williams.appraisalcompany.model.Request.UpsertStaffRequest;
import com.williams.appraisalcompany.model.Response.AppraisalApiResponse;
import com.williams.appraisalcompany.model.Response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/staff")
public class RecruitmentController {

    private AccountFacade accountFacade;

    public RecruitmentController(AccountFacade accountFacade) {
        Assert.notNull(accountFacade);
        this.accountFacade = accountFacade;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> createRecruitmentStaff(
            @RequestHeader("S-User-Token") String userToken,
            @RequestBody UpsertStaffRequest upsertStaffRequest) {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.createStaff(upsertStaffRequest, user);
        return  new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET, path = "/{staffKey}",
            produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> getStaff( @RequestHeader("S-User-Token") String userToken, @PathVariable String staffKey) {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.getStaff(user, staffKey);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> getAllStaff(@RequestHeader("S-User-Token") String userToken, StaffSearchRequest request) {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.getAllStaff(user, request);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.PUT,
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> updateRecruitedStaff(
            @RequestHeader("S-User-Token") String userToken,
            @RequestBody UpsertStaffRequest upsertStaffRequest) {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.updateStaff(upsertStaffRequest, user);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
}
