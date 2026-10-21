package civicscope.controller;

import civicscope.service.ConflitElectionException;
import civicscope.service.ElectionIntrouvableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> gererRequeteInvalide(
            IllegalArgumentException exception
    ) {
        return ResponseEntity.badRequest()
                .body(Map.of("erreur", exception.getMessage()));
    }

    @ExceptionHandler(ElectionIntrouvableException.class)
    public ResponseEntity<Map<String, String>> gererElectionIntrouvable(
            ElectionIntrouvableException exception
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("erreur", exception.getMessage()));
    }

    @ExceptionHandler(ConflitElectionException.class)
    public ResponseEntity<Map<String, String>> gererConflit(
            ConflitElectionException exception
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("erreur", exception.getMessage()));
    }
}