package br.edu.utfpr.iotapi.exceptions;

public class WrongPasswordException extends Exception {
  public WrongPasswordException() {
    super("Senha incorreta!");
  }
}
