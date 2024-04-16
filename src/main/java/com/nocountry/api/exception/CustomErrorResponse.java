package com.nocountry.api.exception;

public record CustomErrorResponse(int status, String message) {
    public CustomErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
