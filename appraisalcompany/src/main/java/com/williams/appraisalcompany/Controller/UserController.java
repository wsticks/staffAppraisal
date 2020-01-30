package com.williams.appraisalcompany.Controller;


import com.williams.appraisalcompany.Service.facade.AccountFacade;
import com.williams.appraisalcompany.Validator.InputValidator;
import com.williams.appraisalcompany.exception.AppraisalApiException;
import com.williams.appraisalcompany.model.Entity.User;
import com.williams.appraisalcompany.model.Request.AuthenticateUserRequest;
import com.williams.appraisalcompany.model.Request.UpsertUserRequest;
import com.williams.appraisalcompany.model.Request.UserSearchRequest;
import com.williams.appraisalcompany.model.Response.AppraisalApiResponse;
import com.williams.appraisalcompany.model.Response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/users")
public class UserController {

    private final AccountFacade accountFacade;

    public UserController(AccountFacade accountFacade) {
        Assert.notNull(accountFacade);
        this.accountFacade = accountFacade;
    }


    @Transactional
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> toCreateUser(
            @Valid @RequestBody UpsertUserRequest upsertUserRequest,
            BindingResult bindingResult) {
        InputValidator.validate(bindingResult);
        SuccessResponse response = accountFacade.createUser(upsertUserRequest);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.PUT, path = "/{userKey}", produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> update(
            @PathVariable String userKey,
            @RequestBody UpsertUserRequest upsertUserRequest,
            @RequestHeader("S-User-Token") String userToken) {
        SuccessResponse response = accountFacade.updateUser(upsertUserRequest, userKey, userToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(path = "/{userKey}", produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> getUserInfo(
            @RequestHeader("S-User-Token") String userToken,
            @PathVariable String userKey) {
        User actor = accountFacade.getAuthenticatedUser(userToken);
        User user = accountFacade.fetchUserByUniqueKey(userKey);
        SuccessResponse successResponse =
                accountFacade.getUserInfo(user, actor);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @RequestMapping(
            path = "/authenticate",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> authenticate(
            @Valid @RequestBody AuthenticateUserRequest request,
            BindingResult bindingResult) throws AppraisalApiException {
        InputValidator.validate(bindingResult);
        SuccessResponse response = accountFacade.authenticateUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @RequestMapping(
//            method = RequestMethod.POST,
//            path = "/passwordreset",
//            produces = "application/json")
//    public ResponseEntity<AppraisalApiResponse> resetPassword(
//            @RequestBody UpsertUserRequest upsertUserRequest
//    ) {
//        SuccessResponse response = accountFacade.resetPassword(upsertUserRequest);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/passwordchange",
            produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> changePassword(
            @RequestBody UpsertUserRequest upsertUserRequest,
            @RequestHeader("S-User-Token") String userToken
    ) {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse response = accountFacade.changeUserPassword(upsertUserRequest, user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/logout",
            produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> logout(
            @RequestHeader("S-User-Token") String userToken
    ) {
        User user = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse response = accountFacade.logoutUser(user, userToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @RequestMapping(
//            method = RequestMethod.POST,
//            path = "/confirmemail",
//            produces = "application/json")
//    public ResponseEntity<AppraisalApiResponse> confirmEmail(
//            @RequestBody ConfirmEmailRequest request) {
//        SuccessResponse response = accountFacade.confirmUserEmail(request);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<AppraisalApiResponse> getUsers(
            @RequestHeader("S-User-Token") String userToken,
            @ModelAttribute UserSearchRequest userSearchRequest)
            throws AppraisalApiException {
        User authenticatedUser = accountFacade.getAuthenticatedUser(userToken);
        SuccessResponse response = accountFacade.getAllUsers(authenticatedUser, userSearchRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
