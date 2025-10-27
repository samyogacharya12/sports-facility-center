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
import java.util.Objects;
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
            List<Booking> bookings = bookingRepository
                .findByFacilityIdAndBookingStatusAndBookingDateAndStartTimeAndEndTime(
                    bookingDto
                        .getFacilityId(),
                    BookingStatus.HALF_BOOKED,
                    bookingDto.getBookingDate().toString(),
                    bookingDto.getStartTime().toString(),
                    bookingDto.getEndTime().toString()
                );
            if (bookingDto.getBookingStatus()
                .equalsIgnoreCase(BookingStatus.HALF_BOOKED.toString())) {
                if (Objects.nonNull(bookings) && !bookings.isEmpty()) {
                    bookings.forEach(booking1 -> {
                        booking1.setBookingStatus(BookingStatus.BOOKED);
                        booking1.setUpdatedDate(LocalDateTime.now().toString());
                        bookingRepository.save(booking1);
                        String startTime = DateUtil.convertTo24Hours(booking1.getStartTime());
                        String endTime = DateUtil.convertTo24Hours(booking1.getEndTime());
                        try {
                            emailService.sendBookingConfirmationEmail(booking1.getUser().getEmail(),
                                booking1.getUser().getName(),
                                booking1.getFacility().getFacilityName(),
                                booking1.getBookingDate(),
                                startTime,
                                endTime,
                                booking1.getBookingStatus().toString());
                        } catch (Exception exception) {
                            log.error("error while sending booking email", exception);
                        }
                    });
                }
            } else if (bookingDto.getBookingStatus()
                .equalsIgnoreCase(BookingStatus.BOOKED.toString())) {
                bookings.forEach(booking1 -> {
                    booking1.setBookingStatus(BookingStatus.CANCELLED);
                    booking1.setUpdatedDate(LocalDateTime.now().toString());
                    bookingRepository.save(booking1);
                    String startTime = DateUtil.convertTo24Hours(booking1.getStartTime());
                    String endTime = DateUtil.convertTo24Hours(booking1.getEndTime());
                    emailService.sendCancellationEmail(booking1.getUser().getEmail(),
                        booking1.getUser().getName(), booking1.getFacility().getFacilityName(),
                        booking1.getBookingDate(), startTime,
                        endTime, false, booking1.getBookingStatus().toString()
                    );
                });
            }
            Booking booking = this.bookingMapper.toEntity(bookingDto);
            booking.setCreatedDate(LocalDateTime.now().toString());
            booking.setUpdatedDate(LocalDateTime.now().toString());
            booking.setStatus(true);
            booking.setDeleted(false);
            if(Objects.nonNull(bookings) && !bookings.isEmpty()){
                booking.setBookingStatus(BookingStatus.BOOKED);
                bookingDto = bookingMapper.toDto(bookingRepository.save(booking));
            }
            else {
                bookingDto = bookingMapper.toDto(bookingRepository.save(booking));
            }
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
    public BookingDto cancelBooking(BookingDto bookingDto) {
        log.info("cancel Booking");
        List<Booking> bookings = bookingRepository
            .findByFacilityIdAndBookingDateAndStartTimeAndEndTime(bookingDto.getFacilityId(),
                bookingDto.getBookingDate().toString(), bookingDto.getStartTime().toString(),
                bookingDto.getEndTime().toString());
        bookings.forEach(booking -> {
            booking.setUpdatedDate(LocalDateTime.now().toString());
            booking.setBookingStatus(BookingStatus.CANCELLED);
            bookingRepository.save(booking);
            String startTime = DateUtil.convertTo24Hours(booking.getStartTime());
            String endTime = DateUtil.convertTo24Hours(booking.getEndTime());
            emailService.sendCancellationEmail(booking.getUser().getEmail(),
                booking.getUser().getName(), booking.getFacility().getFacilityName(),
                booking.getFacility().getFacilityName(),
                startTime, endTime, true,
                booking.getBookingStatus().toString());
        });
        return bookingDto;
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
