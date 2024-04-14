package br.edu.utfpr.iotapi.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({AppException.class})
    public final ResponseEntity<Object> handleAppException(AppException ex) {
        return handleExceptionInternal(ex, new NotValidException(ex.getStatus(), Collections.singletonList(ex.getMessage())
        ), new HttpHeaders(), ex.getStatus(), null);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        return handleExceptionInternal(ex, new NotValidException(HttpStatus.INTERNAL_SERVER_ERROR, Collections.singletonList(ex.getMessage())
        ), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        NotValidException notValidException = new NotValidException(HttpStatus.BAD_REQUEST, errors);

        return new ResponseEntity<>(notValidException, notValidException.status());
    }
}
