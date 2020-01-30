package com.williams.appraisalcompany.model.Request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ConfirmEmailRequest {

    private String email;
}
