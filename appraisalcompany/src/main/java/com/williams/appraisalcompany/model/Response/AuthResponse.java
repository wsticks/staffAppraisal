package com.williams.appraisalcompany.model.Response;

import com.williams.appraisalcompany.Util.TimeUtil;
import com.williams.appraisalcompany.model.Entity.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AuthResponse {

    private String token;
    private String expires;
    private String tokenType;

    public static AuthResponse fromToken(Token token) {
        return AuthResponse.builder()
                .token(token.getToken())
                .expires(TimeUtil.getIsoTime(token.getExpiresAt()))
                .tokenType("bearer")
                .build();
    }
}
