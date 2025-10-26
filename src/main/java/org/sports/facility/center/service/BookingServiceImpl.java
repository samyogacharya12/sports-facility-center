package org.sports.facility.center.service;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.sports.facility.center.dto.BookingDto;
import org.sports.facility.center.dto.TimeSlot;
import org.sports.facility.center.entity.Booking;
import org.sports.facility.center.entity.Facility;
import org.sports.facility.center.enumconstant.BookingStatus;
import org.sports.facility.center.mapper.BookingMapper;
import org.sports.facility.center.repository.BookingRepository;
import org.sports.facility.center.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.DateUtil;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {


    @Autowired
    private BookingRepository bookingRepository;


    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private EmailService emailService;


    @Override
    public BookingDto save(BookingDto bookingDto) {
        log.info("saving booking information");
        try {
            Booking booking = this.bookingMapper.toEntity(bookingDto);
            booking.setCreatedDate(LocalDateTime.now().toString());
            booking.setUpdatedDate(LocalDateTime.now().toString());
            booking.setStatus(true);
            booking.setDeleted(false);
            bookingDto = bookingMapper.toDto(bookingRepository.save(booking));
            String startTime = DateUtil.convertTo24Hours(bookingDto.getStartTime().toString());
            String endTime = DateUtil.convertTo24Hours(bookingDto.getEndTime().toString());
            emailService.sendBookingConfirmationEmail(bookingDto.getEmail(),
                bookingDto.getUsername(), bookingDto.getFacilityName(),
                bookingDto.getBookingDate().toString(),
                startTime, endTime,
                bookingDto.getBookingStatus());
            return bookingDto;
        } catch (Exception exception) {
            log.error("Error while saving booking", exception);
        }
        return null;
    }

    @Override
    public List<BookingDto> findByUserName(BookingDto bookingDto) {
        log.info("findByUserId()=>");
        return bookingMapper.toDto(bookingRepository.findByUserName(bookingDto.getUsername()));
    }

    @Override
    public List<BookingDto> findAll() {
        log.info("findAll()=>");
        return bookingRepository.findAll().stream().map(bookingMapper::toDto).collect(Collectors.toList());
    }


}
