package com.busManager.busmanager.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class PasswordMismatchException extends RuntimeException {
    private HttpStatus status;

    public PasswordMismatchException(String message) {
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
    }
}
