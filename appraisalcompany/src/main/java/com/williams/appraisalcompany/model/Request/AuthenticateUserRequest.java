package com.williams.appraisalcompany.model.Request;


import com.williams.appraisalcompany.model.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticateUserRequest {

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    public User toUser() {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
