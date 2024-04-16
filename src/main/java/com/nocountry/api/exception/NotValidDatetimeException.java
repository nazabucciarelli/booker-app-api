package com.nocountry.api.exception;

public class NotValidDatetimeException extends RuntimeException{
    public NotValidDatetimeException(String message) {
        super(message);
    }
}
