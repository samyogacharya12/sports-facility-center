package org.sports.facility.center.mapper;


import org.sports.facility.center.entity.Booking;
import org.sports.facility.center.dto.*;

import java.util.List;

public interface BookingMapper {


    BookingDto toDto(Booking booking);

    List<BookingDto> toDto(List<Booking> bookings);


    Booking toEntity(BookingDto bookingDto);
}
