package it.epicode.U2J_W4_D5_PROJECT.modules.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerClass extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<ErrorMessage> entityNotFound(EntityNotFoundException ex) {
        ErrorMessage e = new ErrorMessage();
        e.setMessage("Error from class: " + ex.getMessage());
        e.setStatusCode(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    protected ResponseEntity<ErrorMessage> alreadyExists(AlreadyExistsException ex) {
        ErrorMessage e = new ErrorMessage();
        e.setMessage("Error from class: " + ex.getMessage());
        e.setStatusCode(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UploadException.class)
    protected ResponseEntity<ErrorMessage> uploadExceptionHandler(UploadException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setStatusCode(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), "Errore controller: " + error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, ErrorMessage>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, ErrorMessage> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            if (fieldName.contains(".")) {
                fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
            }

            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage(violation.getMessage());
            errorMessage.setStatusCode(HttpStatus.BAD_REQUEST);
            errors.put(fieldName, errorMessage);
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}