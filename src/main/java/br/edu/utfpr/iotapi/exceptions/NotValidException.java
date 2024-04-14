package br.edu.utfpr.iotapi.exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;

public record NotValidException(HttpStatus status, List<String> errors) {
}
