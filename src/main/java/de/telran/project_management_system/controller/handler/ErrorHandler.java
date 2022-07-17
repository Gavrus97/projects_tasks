package de.telran.project_management_system.controller.handler;

import de.telran.project_management_system.dto.error.HttpErrorStatusResponseDTO;
import de.telran.project_management_system.dto.error.PathVariableValidationErrorResponseDTO;
import de.telran.project_management_system.dto.error.ProgressStatusConvertErrorResponseDTO;
import de.telran.project_management_system.dto.error.ValidationErrorResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<HttpErrorStatusResponseDTO> handleStatusError(ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(HttpErrorStatusResponseDTO
                        .builder()
                        .message(ex.getReason())
                        .status(ex.getStatus())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponseDTO> handleValidationError(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors()
                .stream()
                .collect(Collectors
                        .groupingBy(
                                FieldError::getField,
                                Collectors
                                        .mapping(
                                                DefaultMessageSourceResolvable::getDefaultMessage,
                                                Collectors.toList()
                                        )
                        )
                );
        var response = ValidationErrorResponseDTO
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .errors(errors)
                .build();

        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProgressStatusConvertErrorResponseDTO> handleConvertStatus(
            MethodArgumentTypeMismatchException ex){
        var body = ProgressStatusConvertErrorResponseDTO
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getLocalizedMessage())
                .build();
        return ResponseEntity
                .status(body.getStatus())
                .body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<PathVariableValidationErrorResponseDTO> handleConstraintViolationException(
            ConstraintViolationException ex){
        var body = PathVariableValidationErrorResponseDTO
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .build();

        return ResponseEntity
                .status(body.getStatus())
                .body(body);
    }
}
