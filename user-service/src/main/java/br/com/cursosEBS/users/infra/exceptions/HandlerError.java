package br.com.cursosEBS.users.infra.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class HandlerError {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity handlerError404(UsernameNotFoundException ex) {
        return ResponseEntity.status(404).body(ex);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handlerError400(ValidationException ex) {
        return ResponseEntity.status(400).body(ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handlerError400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();

        //erros me da uma stream e mape-e cada objeto field erro para um objeto erro validacao e me devolvendo uma lista
        return ResponseEntity.badRequest().body(erros.get(0).getDefaultMessage());
    }
}
