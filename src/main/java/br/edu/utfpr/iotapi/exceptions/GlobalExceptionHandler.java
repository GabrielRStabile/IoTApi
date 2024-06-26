package br.edu.utfpr.iotapi.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    @ExceptionHandler({UsernameNotFoundException.class, AuthenticationException.class})
    public final ResponseEntity<Object> handleUsernameNotFoundException(Exception ex) {
        return handleExceptionInternal(ex, new NotValidException(HttpStatus.NOT_FOUND, Collections.singletonList("Usuário ou senha incorretos.")
        ), new HttpHeaders(), HttpStatus.NOT_FOUND, null);
    }

    @ExceptionHandler({JWTVerificationException.class})
    public final ResponseEntity<Object> handleJWTVerificationException(JWTVerificationException ex) {
        return handleExceptionInternal(ex, new NotValidException(HttpStatus.UNAUTHORIZED, Collections.singletonList("Token inválido.")
        ), new HttpHeaders(), HttpStatus.UNAUTHORIZED, null);
    }

    @ExceptionHandler({AppException.class})
    public final ResponseEntity<Object> handleAppException(AppException ex) {
        return handleExceptionInternal(ex, new NotValidException(ex.getStatus(), Collections.singletonList(ex.getMessage())
        ), new HttpHeaders(), ex.getStatus(), null);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        return handleExceptionInternal(ex, new NotValidException(HttpStatus.INTERNAL_SERVER_ERROR, Collections.singletonList("Erro interno.")
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
