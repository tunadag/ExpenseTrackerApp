package com.tunadag.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionType {

    UNEXPECTED_ERROR(9000, "Unexpected Error! Please submit a report.", INTERNAL_SERVER_ERROR),

    RESOURCE_NOT_FOUND(9001, "Resource is not Found", NOT_FOUND),

    USER_ALREADY_EXISTS(9002, "User already exists!", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(9003, "User not found!", INTERNAL_SERVER_ERROR),
    ACCOUNT_NOT_FOUND(9004, "Account not found!", INTERNAL_SERVER_ERROR),
    TRANSACTION_NOT_FOUND(9005, "Transaction not found!", INTERNAL_SERVER_ERROR);

    private int code;
    private String message;
    private HttpStatus httpStatus;
}
