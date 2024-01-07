package com.busManager.busmanager.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class SearchBusException extends RuntimeException {
    private HttpStatus status;
    public SearchBusException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
}
