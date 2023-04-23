package com.hostfully.bookingservice.models.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BlockRequest {
    @NotBlank(message = "startDate cannot be blank")
    private String startDate;

    @NotBlank(message = "endDate cannot be blank")
    private String endDate;

    @NotBlank(message = "propertyName cannot be blank")
    private String propertyName;
}
