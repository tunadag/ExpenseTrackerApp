package com.tunadag.exceptions.custom;

public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(String message) {super(message);}
}
