package com.hostfully.bookingservice.enums;

import org.springframework.http.HttpStatus;

public enum CustomErrorMessage {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Oops! Something went wrong! Help us improve your experience by sending an error report"),
    UNPROCESSABLE_ENTITY(HttpStatus.UNPROCESSABLE_ENTITY, "The given data was invalid."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not found."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthenticated."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Access denied! You don't have the necessary privilege to access this resource.");

    final HttpStatus httpStatus;
    final String message;

    CustomErrorMessage(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
