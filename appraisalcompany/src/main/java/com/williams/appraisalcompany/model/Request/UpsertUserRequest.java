package com.williams.appraisalcompany.model.Request;

import com.williams.appraisalcompany.model.Constant.Status;
import com.williams.appraisalcompany.model.Entity.User;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class UpsertUserRequest {

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String address;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String phone;
    private String roleId;
    private Status status;

    public User toUser(){
        User user = new User();
        user.setAddress(address);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setRoleId(roleId);
        if (status != null) {
            user.setStatus(status);
        }
        return user;
    }
}

