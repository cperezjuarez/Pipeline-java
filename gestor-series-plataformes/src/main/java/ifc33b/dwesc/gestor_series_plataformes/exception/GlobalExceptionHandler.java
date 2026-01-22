package ifc33b.dwesc.gestor_series_plataformes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // Plataforma no encontrada
    @ExceptionHandler(PlataformaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePlataformaNotFoundException(PlataformaNotFoundException ex) {
        ErrorResponse error = ErrorResponse.create(ex, HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
