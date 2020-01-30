package com.williams.appraisalcompany.Controller;

import com.williams.appraisalcompany.Service.facade.AccountFacade;
import com.williams.appraisalcompany.model.Entity.User;
import com.williams.appraisalcompany.model.Request.UpsertDepartmentRequest;
import com.williams.appraisalcompany.model.Response.AppraisalApiResponse;
import com.williams.appraisalcompany.model.Response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/department")
public class DepartmentController {

    private AccountFacade accountFacade;

    public DepartmentController(AccountFacade accountFacade) {
        Assert.notNull(accountFacade);
        this.accountFacade = accountFacade;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> createDepartment(
            @RequestHeader("S-User-Token") String userToken,
            @RequestBody UpsertDepartmentRequest upsertDepartmentRequest){
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.createDepartment(upsertDepartmentRequest, user);
        return  new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET, path = "/{departmentKey}",
            produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> getDepartment(
            @RequestHeader("S-User-Token") String userToken, @PathVariable String departmentKey) {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.getDepartment(user, departmentKey);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> getAllDepartments(@RequestHeader("S-User-Token") String userToken) {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.getAllDepartments(user);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.PUT,
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> updateDeparment(
            @RequestHeader("S-User-Token") String userToken,
            @RequestBody UpsertDepartmentRequest upsertDepartmentRequest ) {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse successResponse = accountFacade.updateDepartment(upsertDepartmentRequest, user);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
}
