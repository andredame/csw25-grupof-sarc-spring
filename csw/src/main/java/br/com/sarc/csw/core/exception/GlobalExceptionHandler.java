package br.com.sarc.csw.core.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoIndisponivelException.class)
    public ResponseEntity<MensagemErroDTO> handleRecursoIndisponivel(RecursoIndisponivelException ex) {
        return ResponseEntity.badRequest().body(new MensagemErroDTO(ex.getMessage()));
    }
}
