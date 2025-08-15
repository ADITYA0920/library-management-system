package com.LMS.libraryMngSystem.web.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private Map<String, Object> details;

    public ErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(HttpStatus status, String message, Map<String, Object> details) {
        this.status = status;
        this.message = message;
        this.details = details;
    }
}
