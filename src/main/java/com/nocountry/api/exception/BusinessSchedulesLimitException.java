package com.nocountry.api.exception;

public class BusinessSchedulesLimitException extends RuntimeException{
    public BusinessSchedulesLimitException(String message) {
        super(message);
    }
}
