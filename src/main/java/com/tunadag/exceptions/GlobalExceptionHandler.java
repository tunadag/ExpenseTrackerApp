package com.tunadag.exceptions;

import com.tunadag.exceptions.custom.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource){this.messageSource = messageSource;}

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception exception, HttpServletRequest request) {
        log.error(messageSource.getMessage("exception.UNEXPECTED_ERROR", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(ExceptionType.UNEXPECTED_ERROR, exception, request);
    }


    private ResponseEntity<ExceptionResponse> createExceptionInfoResponse(ExceptionType exceptionType, Exception exception, HttpServletRequest request) {
        String exceptionName = exceptionType.name();
        Locale locale;
        String acceptLanguage = request.getHeader("Accept-Language");
        if (acceptLanguage != null && acceptLanguage.toLowerCase().startsWith("tr")) {
            locale = Locale.getDefault();
        } else {
            locale = Locale.US;
        }
        String localizedMessage = messageSource.getMessage("exception." + exceptionName, null, locale);
        return new ResponseEntity<>(ExceptionResponse.builder()
                .exceptionCode(exceptionType.getCode())
                .customMessage(localizedMessage)
                .exceptionMessage(exception.getMessage())
                .httpStatus(exceptionType.getHttpStatus().value())
                .build(), exceptionType.getHttpStatus());
    }

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.RESOURCE_NOT_FOUND", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(ExceptionType.RESOURCE_NOT_FOUND, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleUserAlreadyExistsException(UserAlreadyExistsException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.USER_ALREADY_EXISTS", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(ExceptionType.USER_ALREADY_EXISTS, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.USER_NOT_FOUND", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(ExceptionType.USER_NOT_FOUND, exception, request);
    }
    @ResponseBody
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleAccountNotFoundException(AccountNotFoundException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.ACCOUNT_NOT_FOUND", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(ExceptionType.ACCOUNT_NOT_FOUND, exception, request);
    }

    @ResponseBody
    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleTransactionNotFoundException(TransactionNotFoundException exception, HttpServletRequest request) {
        log.warn(messageSource.getMessage("exception.TRANSACTION_NOT_FOUND", null, Locale.getDefault()), exception);
        return createExceptionInfoResponse(ExceptionType.TRANSACTION_NOT_FOUND, exception, request);
    }
}
