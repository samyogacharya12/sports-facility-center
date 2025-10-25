package org.sports.facility.center.service;


import org.sports.facility.center.dto.BookingDto;
import org.sports.facility.center.dto.FacilityDto;
import org.sports.facility.center.dto.TimeSlot;

import java.util.List;

public interface FacilityService {

    FacilityDto save(FacilityDto facilityDto);


    List<FacilityDto> findAll();

    List<FacilityDto> findByBookingDate(BookingDto bookingDto);

    List<TimeSlot> getAvailableSlots(BookingDto bookingDto);
}
