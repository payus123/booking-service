package com.hostfully.bookingservice.models.dtos;

import com.hostfully.bookingservice.enums.CustomErrorMessage;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ErrorResponse {
    private final Error error;

    public ErrorResponse(String message) {
        this(message, new HashMap<>());
    }

    public ErrorResponse(CustomErrorMessage customErrorMessage) {
        this(customErrorMessage, new HashMap<>());
    }

    public ErrorResponse(CustomErrorMessage customErrorMessage, Map<String, String> reasons) {
        this(customErrorMessage.getMessage(), reasons);
    }

    public ErrorResponse(String message, Map<String, String> reasons) {
        this.error = new Error(message, reasons);
    }

    public record Error(String message, Map<String, String> reasons) {
        public Error(String message, Map<String, String> reasons) {
            this.message = message;
            this.reasons = reasons == null ? new HashMap<>() : reasons;
        }
    }
}
