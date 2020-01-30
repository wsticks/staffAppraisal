package com.williams.appraisalcompany.exception;

public abstract class AppraisalApiException extends RuntimeException  {

    AppraisalApiException(String message) {
        super(message);
    }

    AppraisalApiException(String message, Throwable cause) {
        super(message, cause);
        if (this.getCause() == null && cause != null) {
            this.initCause(cause);
        }
    }
}
