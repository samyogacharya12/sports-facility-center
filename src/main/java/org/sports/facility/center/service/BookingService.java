package org.sports.facility.center.service;

import jakarta.mail.MessagingException;
import org.sports.facility.center.dto.BookingDto;
import org.sports.facility.center.dto.TimeSlot;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {


    BookingDto save(BookingDto bookingDto);


    BookingDto cancelBooking(BookingDto bookingDto);

    List<BookingDto> findByUserName(BookingDto bookingDto);

    List<BookingDto> findAll();


}
