package com.hostfully.bookingservice.models.dtos.request;

import com.hostfully.bookingservice.annotations.ValidateEnum;
import com.hostfully.bookingservice.enums.BookingAction;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

@Data
public class BookingUpdateRequest {
    @NotBlank(message = "bookingId cannot be blank")
    private String bookingId;
    @NotBlank(message = "action cannot be blank")
    @ValidateEnum(enumClass = BookingAction.class)
    private String action;
    private Map<String,Object>  params;

}
