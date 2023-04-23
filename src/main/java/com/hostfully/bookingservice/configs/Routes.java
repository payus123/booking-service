package com.hostfully.bookingservice.configs;

public class Routes {
    public static class Exception {
        public static final String DEFAULT_ERROR = "/error";
    }
    public static final String BASE_ROUTE = "/booking-service";
    public static final String CREATE_BOOKING = "/create-booking";
    public static final String UPDATE_BOOKING = "/update-booking";
    public static final String READ_BOOKING= "/read-booking/{bookingId}";
    public static final String READ_ALL_BOOKINGS= "/read-all-bookings";
    public static final String READ_ALL_BLOCKS= "/read-all-blocks";
    public static final String CREATE_BLOCK = "/create-block";
    public static final String DELETE_BLOCK = "/delete-block/{blockId}";

}
