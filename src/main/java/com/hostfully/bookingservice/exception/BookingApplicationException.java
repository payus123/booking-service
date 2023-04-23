package com.hostfully.bookingservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class BookingApplicationException extends RuntimeException {
    private final HttpStatus httpStatus;
    private Map<String, String> reasons;

    public BookingApplicationException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public BookingApplicationException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public BookingApplicationException(Map<String, String> reasons) {
        super("The given data was invalid.");
        this.httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        this.reasons = reasons;
    }
}
