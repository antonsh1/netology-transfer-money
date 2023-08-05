package ru.smartjava.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.smartjava.dto.ErrorMessage;
import ru.smartjava.exceptions.InvalidCode;
import ru.smartjava.exceptions.InvalidOperationId;
import ru.smartjava.exceptions.TransferClosed;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleInternalErrorExceptions() {
        return new ResponseEntity<>(new ErrorMessage("Внутренняя ошибка сервера", HttpStatus.INTERNAL_SERVER_ERROR.value()),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(new ErrorMessage(errors.toString(), HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCode.class)
    public ResponseEntity<ErrorMessage> handleInvalidCode(InvalidCode exception) {
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidOperationId.class)
    public ResponseEntity<ErrorMessage> handleInvalidTransactionId(InvalidOperationId exception) {
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransferClosed.class)
    public ResponseEntity<ErrorMessage> handleTransferClosed(TransferClosed exception) {
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }
}
