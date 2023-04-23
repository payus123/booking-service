package com.hostfully.bookingservice.models.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hostfully.bookingservice.models.Booking;
import com.hostfully.bookingservice.utils.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private String guestName;
    private String bookingId;
    private String propertyName;
    @JsonFormat(pattern = CommonConstants.DATETIME_FORMAT)
    private String startDate;
    @JsonFormat(pattern = CommonConstants.DATETIME_FORMAT)
    private String endDate;
    private String actionPerformed;
    private String status;


    public static BookingResponse  getBookingResponse(Booking booking, String actionPerformed){
        return BookingResponse.builder()
                .propertyName(booking.getPropertyName())
                .startDate(booking.getStartDate().toString())
                .endDate(booking.getEndDate().toString())
                .actionPerformed(actionPerformed)
                .guestName(booking.getGuestName())
                .status(booking.getBookingStatus().name())
                .bookingId(booking.getUniqueId()).build();
    }
}
