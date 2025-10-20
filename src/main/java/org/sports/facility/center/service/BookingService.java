package org.sports.facility.center.service;

import org.sports.facility.center.dto.BookingDto;
import org.sports.facility.center.dto.TimeSlot;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {


    BookingDto save(BookingDto bookingDto);

    List<BookingDto> findAll();


}
