package com.hostfully.bookingservice.models;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Table(name = "block")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Block  extends BaseModel{
    private Date startDate;
    private Date endDate;
    private String propertyName;
    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking bookingId;

}
