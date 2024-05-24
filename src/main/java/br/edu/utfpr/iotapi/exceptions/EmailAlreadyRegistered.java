package br.edu.utfpr.iotapi.exceptions;

import org.springframework.http.HttpStatus;

public class EmailAlreadyRegistered extends AppException {
    public EmailAlreadyRegistered() {
        super("Email já cadastrado", HttpStatus.BAD_REQUEST);
    }
}
