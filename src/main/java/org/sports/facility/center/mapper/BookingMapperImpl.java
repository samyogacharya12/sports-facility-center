package org.sports.facility.center.mapper;

import org.sports.facility.center.entity.Booking;
import org.sports.facility.center.dto.*;
import org.sports.facility.center.enumconstant.BookingStatus;
import org.sports.facility.center.repository.FacilityRepository;
import org.sports.facility.center.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingMapperImpl implements BookingMapper{


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    private static BookingDto mapToBookingDto(Booking booking){
        BookingDto bookingDto=new BookingDto();
        bookingDto.setBookingDate(LocalDate.parse(booking.getBookingDate()));
        bookingDto.setStartTime(LocalTime.parse(booking.getStartTime()));
        bookingDto.setEndTime(LocalTime.parse(booking.getEndTime()));
        bookingDto.setBookingStatus(booking.getBookingStatus().toString());
        bookingDto.setImageUrl(booking.getFacility().getImageUrl());
        bookingDto.setUserId(booking.getUser().getId());
        bookingDto.setUsername(booking.getUser().getName());
        bookingDto.setFacilityId(booking.getFacility().getId());
        bookingDto.setFacilityName(booking.getFacility().getFacilityName());
        return bookingDto;
    }

    @Override
    public BookingDto toDto(Booking booking) {
        return mapToBookingDto(booking);
    }

    @Override
    public List<BookingDto> toDto(List<Booking> bookings) {
        return bookings.stream().map(BookingMapperImpl::mapToBookingDto).collect(Collectors.toList());
    }

    @Override
    public Booking toEntity(BookingDto bookingDto) {
        return Booking.builder()
            .bookingDate(bookingDto.getBookingDate().toString())
            .user(userRepository.findById(bookingDto.getUserId()).get())
            .bookingStatus(Enum.valueOf(BookingStatus.class, bookingDto.getBookingStatus()))
            .facility(facilityRepository.findById(bookingDto.getFacilityId()).get())
            .startTime(bookingDto.getStartTime().toString())
            .endTime(bookingDto.getEndTime().toString())
            .build();
    }
}
