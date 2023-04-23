package com.hostfully.bookingservice.repositories;


import com.hostfully.bookingservice.models.Block;
import com.hostfully.bookingservice.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {

    @Query(value = "from Block b where :startDate <= b.endDate and :endDate >= b.startDate and b.propertyName = :propertyName")
    List<Block> getAllBlocksInDates( @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("propertyName") String propertyName);
    Optional<Block> findBlockByBookingId(Booking bookingId);
    Optional<Block> findBlockByUniqueId(String blockId);
}
