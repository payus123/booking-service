package com.hostfully.bookingservice.repositories;


import com.hostfully.bookingservice.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
         Optional<Booking> findBookingByUniqueId(String uniqueId);
}
