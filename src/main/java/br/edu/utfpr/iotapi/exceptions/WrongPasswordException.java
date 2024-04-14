package br.edu.utfpr.iotapi.exceptions;

import org.springframework.http.HttpStatus;

public class WrongPasswordException extends AppException {
  public WrongPasswordException() {
    super("Senha incorreta", HttpStatus.BAD_REQUEST)                                                                                                                             ;
  }
}
