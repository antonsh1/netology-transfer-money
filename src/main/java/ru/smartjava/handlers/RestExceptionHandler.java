package ru.smartjava.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.smartjava.exceptions.InvalidCode;
import ru.smartjava.exceptions.InvalidOperationId;
import ru.smartjava.exceptions.TransferClosed;
import ru.smartjava.objects.ErrorMessage;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorMessage handleInternalErrorExceptions(
            Exception ex) {
        return new ErrorMessage("Внутренняя ошибка сервера", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorMessage handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = "";
            switch (fieldName) {
                case "cardFromNumber":
                    fieldName = "Ошибка номера карты источника перевода";
                    break;
                case "cardFromValidTill":
                    fieldName = "Неверные данные срока действия карты";
                    break;
                case "cardFromCVV":
                    fieldName = "Неверный CVV код";
                    break;
                case "cardToNumber":
                    fieldName = "Ошибка номера карты получения перевода";
                    break;
                case "amount.value":
                    fieldName = "Ошибка в сумме перевода";
                    break;
                case "amount.currency":
                    fieldName = "Ошибка в валюте перевода";
                    break;
                default:
                    fieldName = "Неопознанная ошибка " + fieldName;
                    break;
            }
            System.out.println(((FieldError) error).getField());
            errorMessage += "(" + error.getDefaultMessage() + ")";
            errors.put(fieldName, errorMessage);
        });
        return new ErrorMessage(errors.toString(), HttpStatus.BAD_REQUEST.value());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidCode.class)
    public ErrorMessage handleInvalidCode(InvalidCode exception) {
        return new ErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidOperationId.class)
    public ErrorMessage handleInvalidTransactionId(InvalidOperationId exception) {
        return new ErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TransferClosed.class)
    public ErrorMessage handleTransferClosed(TransferClosed exception) {
        return new ErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
    }
}
