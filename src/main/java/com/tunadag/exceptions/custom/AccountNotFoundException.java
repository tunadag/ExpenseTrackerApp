package com.tunadag.exceptions.custom;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String message) {super(message);}
}
