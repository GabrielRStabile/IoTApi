package br.edu.utfpr.iotapi.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends Exception {
    private final String message;
    private final HttpStatus status;

    public AppException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
