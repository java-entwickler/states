package com.lera.autocomplete.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class StateAlreadyExistsException extends RuntimeException {
    public StateAlreadyExistsException(String exception) {
        super(exception);
    }
}
