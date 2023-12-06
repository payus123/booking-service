package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.models.dtos.request.BlockRequest;
import com.hostfully.bookingservice.models.dtos.request.BookingRequest;
import com.hostfully.bookingservice.models.dtos.request.BookingUpdateRequest;
import com.hostfully.bookingservice.models.dtos.request.PageDto;
import com.hostfully.bookingservice.models.dtos.response.BlockResponse;
import com.hostfully.bookingservice.models.dtos.response.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest bookingRequest);

    BookingResponse updateBooking(BookingUpdateRequest request);

    List<BookingResponse> fetchAllBookings(PageDto pageDto);

    List<BlockResponse> fetchAllBlocks(PageDto pageDto);

    BlockResponse createBlock(BlockRequest blockRequest);

    BookingResponse fetchBooking(String bookingId);

    BlockResponse deleteBlock(String blockId);



}
