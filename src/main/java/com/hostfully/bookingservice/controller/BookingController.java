package com.hostfully.bookingservice.controller;

import com.hostfully.bookingservice.models.dtos.Wrapper;
import com.hostfully.bookingservice.models.dtos.request.BlockRequest;
import com.hostfully.bookingservice.models.dtos.response.BlockResponse;
import com.hostfully.bookingservice.models.dtos.request.BookingRequest;
import com.hostfully.bookingservice.models.dtos.request.BookingUpdateRequest;
import com.hostfully.bookingservice.models.dtos.response.BookingResponse;
import com.hostfully.bookingservice.service.BookingService;
import com.hostfully.bookingservice.utils.ResponseBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.hostfully.bookingservice.configs.Routes.BASE_ROUTE;
import static com.hostfully.bookingservice.configs.Routes.CREATE_BLOCK;
import static com.hostfully.bookingservice.configs.Routes.CREATE_BOOKING;
import static com.hostfully.bookingservice.configs.Routes.DELETE_BLOCK;
import static com.hostfully.bookingservice.configs.Routes.READ_BOOKING;
import static com.hostfully.bookingservice.configs.Routes.UPDATE_BOOKING;

@RestController
@RequestMapping(BASE_ROUTE)
@RequiredArgsConstructor
public class BookingController {
 private final BookingService bookingService;

    @PostMapping(CREATE_BOOKING)
    public ResponseEntity<Wrapper<BookingResponse>> createBooking(@Validated  @RequestBody BookingRequest request) {
        return ResponseBuilder.success(bookingService.createBooking(request));
    }
    @PostMapping(UPDATE_BOOKING)
    public ResponseEntity<Wrapper<BookingResponse>> updateBooking(@Validated  @RequestBody BookingUpdateRequest request) {
        return ResponseBuilder.success(bookingService.updateBooking(request));
    }
    @GetMapping(READ_BOOKING)
    public ResponseEntity<Wrapper<BookingResponse>> readBooking(@RequestParam(name = "bookingId") String bookingId) {
        return ResponseBuilder.success(bookingService.fetchBooking(bookingId));
    }
    @PostMapping(CREATE_BLOCK)
    public ResponseEntity<Wrapper<BlockResponse>> createBlock(@Valid @RequestBody BlockRequest request) {
        return ResponseBuilder.success(bookingService.createBlock(request));
    }
    @PostMapping(DELETE_BLOCK)
    public ResponseEntity<Wrapper<BlockResponse>> deleteBlock(@RequestParam String blockId) {
        return ResponseBuilder.success(bookingService.deleteBlock(blockId));
    }

}
