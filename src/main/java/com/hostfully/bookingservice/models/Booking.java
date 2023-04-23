package com.hostfully.bookingservice.models;

import com.hostfully.bookingservice.enums.BookingStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Table(name = "booking")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Booking extends BaseModel{
    private Date startDate;
    private Date endDate;
    private String guestName;
    private String propertyName;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
}
