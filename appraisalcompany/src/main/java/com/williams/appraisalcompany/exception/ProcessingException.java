package com.williams.appraisalcompany.exception;

public class ProcessingException extends AppraisalApiException {


    public ProcessingException(String message) {
        super(message);
    }

    public ProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
