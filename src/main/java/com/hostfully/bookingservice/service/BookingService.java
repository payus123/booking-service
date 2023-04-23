package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.enums.BookingAction;
import com.hostfully.bookingservice.enums.BookingStatus;
import com.hostfully.bookingservice.exception.BookingApplicationException;
import com.hostfully.bookingservice.models.Block;
import com.hostfully.bookingservice.models.Booking;
import com.hostfully.bookingservice.models.dtos.request.BlockRequest;
import com.hostfully.bookingservice.models.dtos.request.BookingRequest;
import com.hostfully.bookingservice.models.dtos.request.BookingUpdateRequest;
import com.hostfully.bookingservice.models.dtos.request.PageDto;
import com.hostfully.bookingservice.models.dtos.response.BlockResponse;
import com.hostfully.bookingservice.models.dtos.response.BookingResponse;
import com.hostfully.bookingservice.repositories.BlockRepository;
import com.hostfully.bookingservice.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.hostfully.bookingservice.annotations.DateFormatValidator.isValidFormat;
import static com.hostfully.bookingservice.models.dtos.response.BlockResponse.getBlockResponse;
import static com.hostfully.bookingservice.models.dtos.response.BookingResponse.getBookingResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final BlockRepository blockRepository;
    private final BookingRepository bookingRepository;

    public static boolean checkIfParamHasNull(List<Objects> params) {
        return Stream.of(params).noneMatch(Objects::isNull);

    }

    public static void checkStartDateIsBeforeEndDate(Date startDate, Date endDate) {
        if ((startDate.after(endDate))) {
            throw new BookingApplicationException("startDate cannot be after endDate");
        }
    }

    @SneakyThrows
    @Transactional
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        Date startDate = resolveDate(bookingRequest.getStartDate());
        Date endDate = resolveDate(bookingRequest.getEndDate());
        String propertyName = bookingRequest.getPropertyName();
        String guestName = bookingRequest.getGuestName();

        checkIfBlocked(startDate, endDate, propertyName);
        checkStartDateIsBeforeEndDate(startDate, endDate);



        Booking booking = bookingRepository.save(Booking.builder()
                .guestName(guestName)
                .propertyName(propertyName)
                .startDate(startDate)
                .endDate(endDate)
                .bookingStatus(BookingStatus.BOOKED)
                .build());

        blockRepository.save(Block.builder().propertyName(
                        booking.getPropertyName())
                .bookingId(booking)
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build());

        return getBookingResponse(booking, "Booking Created");
    }

    @Transactional
    public BookingResponse updateBooking(BookingUpdateRequest request) {
        String bookingId = request.getBookingId();
        BookingResponse response = new BookingResponse();
        Booking booking = findBooking(bookingId);
        switch (BookingAction.valueOf(request.getAction())) {

            case CANCEL -> response = getBookingResponse(cancelBooking(booking), "Booking Canceled");

            case REBOOK -> response = getBookingResponse(rebook(request, booking), "Booking Rebooked");

            default -> throw new BookingApplicationException("Action not Valid: must be CANCEL or REBOOK");

        }
        return response;

    }

    public List<BookingResponse> fetchAllBookings(PageDto pageDto){
         Pageable pageable = PageRequest.of(pageDto.getPageNo(), pageDto.getPageSize(), Sort.by("id").descending());
        List<BookingResponse> bookingResponseList = new ArrayList<>();
         bookingRepository.findAll(pageable).stream().forEach(b->bookingResponseList.add(getBookingResponse(b,"read all bookings")));
         return bookingResponseList;
    }

    public List<BlockResponse> fetchAllBlocks(PageDto pageDto){
        Pageable pageable = PageRequest.of(pageDto.getPageNo(), pageDto.getPageSize(), Sort.by("id").descending());
        List<BlockResponse> blockResponseList = new ArrayList<>();
        blockRepository.findAll(pageable).stream().forEach(b->blockResponseList.add(getBlockResponse(b,"read all blocks")));
        return blockResponseList;
    }

    private Booking findBooking(String bookingId) {
        return bookingRepository.findBookingByUniqueId(bookingId)
                .orElseThrow(() -> new BookingApplicationException("Booking with  id: " + bookingId + "does not exist"));
    }

    public BookingResponse fetchBooking(String bookingId) {
        log.info("about fetching booking ");
        return getBookingResponse(findBooking(bookingId), "Fetch Booking");

    }

    @SneakyThrows
    public BlockResponse createBlock(BlockRequest blockRequest) {
        Date startDate = resolveDate(blockRequest.getStartDate());
        Date endDate = resolveDate(blockRequest.getEndDate());
        String propertyName = blockRequest.getPropertyName();
        checkIfBlocked(startDate, endDate, propertyName);


        Block block = blockRepository.save(Block.builder().propertyName(propertyName)
                .bookingId(null)
                .startDate(startDate)
                .endDate(endDate)
                .build());
        log.info("block created successfully ");
        return getBlockResponse(block, "Create Block");

    }

    public BlockResponse deleteBlock(String blockId) {
        Block block = blockRepository.findBlockByUniqueId(blockId)
                .orElseThrow(() -> new BookingApplicationException("Block with  id: " + blockId + "does not exist"));
        if (!Objects.isNull(block.getBookingId())) {
            log.info("cannot delete bound block ");
            throw new BookingApplicationException("Cannot directly delete a block that is tied to a booking: cancel or rebook booking");
        }
        blockRepository.delete(block);
        return getBlockResponse(block, "Deleted Block");
    }

    private void checkIfBlocked(Date startDate, Date endDate, String propertyName) {

        List<Block> blocks = blockRepository.getAllBlocksInDates(startDate
                , endDate, propertyName);
        if (!blocks.isEmpty()) {
            throw new BookingApplicationException("Selected dates are already booked, choose other dates for booking");
        }

    }

    private Booking cancelBooking(Booking booking) {
        Block block = blockRepository.findBlockByBookingId(booking)
                .orElseThrow(() -> new BookingApplicationException("Block not found for booking Id, must have been canceled already"));
        blockRepository.delete(block);
        Booking updatedBooking = booking;
        updatedBooking.setBookingStatus(BookingStatus.CANCELED);
        return bookingRepository.save(updatedBooking);
    }

    @SneakyThrows
    private Booking rebook(BookingUpdateRequest updateRequest, Booking booking) {
        String requestStartDate = (String) updateRequest.getParams().get("startDate");
        String requestEndDate = (String) updateRequest.getParams().get("enDate");
        String requestGuestName = (String) updateRequest.getParams().get("guestName");
        //Did not collect propertyName because ideally it's rebooking
        Date startDate = Objects.isNull(requestStartDate) ? booking.getStartDate() : resolveDate(requestStartDate);
        Date endDate =  Objects.isNull(requestEndDate) ? booking.getEndDate() : resolveDate(requestEndDate);
        String guestName = Objects.isNull(requestGuestName) ? booking.getPropertyName() : requestGuestName;
        checkStartDateIsBeforeEndDate(startDate, endDate);

        Booking bookingToRebook = cancelBooking(booking);
        bookingToRebook.setBookingStatus(BookingStatus.BOOKED);
        bookingToRebook.setGuestName(guestName);
        bookingToRebook.setStartDate(startDate);
        bookingToRebook.setEndDate(endDate);
        bookingToRebook.setPropertyName(booking.getPropertyName());



        blockRepository.save(Block.builder().propertyName(
                        booking.getPropertyName())
                .bookingId(booking)
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build());
        
        return bookingRepository.save(bookingToRebook);

    }

    private Date resolveDate(String date) throws ParseException {

        if (!isValidFormat("dd/MM/yyyy", date)) {
            throw new BookingApplicationException("Date format is invalid for: "+ date+" ,use  dd/MM/yyyy");
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = formatter.format(new Date());
        Date dateResolved = formatter.parse(date);
        Date today = formatter.parse(todayDate);
        if (dateResolved.before(today)) {
            throw new BookingApplicationException("Date cannot be in the past");
        }
        return dateResolved;
    }


}
