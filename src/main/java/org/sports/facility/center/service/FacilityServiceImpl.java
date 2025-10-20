package org.sports.facility.center.service;

import lombok.extern.slf4j.Slf4j;
import org.sports.facility.center.dto.BookingDto;
import org.sports.facility.center.dto.FacilityDto;
import org.sports.facility.center.dto.TimeSlot;
import org.sports.facility.center.entity.Booking;
import org.sports.facility.center.entity.Facility;
import org.sports.facility.center.mapper.FacilityMapper;
import org.sports.facility.center.repository.BookingRepository;
import org.sports.facility.center.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FacilityServiceImpl implements FacilityService {

    @Autowired
    private FacilityMapper facilityMapper;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public FacilityDto save(FacilityDto facilityDto) {
        log.info("saving facility");
        try {
            Facility facility = this.facilityMapper.toEntity(facilityDto);
            facility.setCreatedDate(LocalDateTime.now().toString());
            facility.setUpdatedDate(LocalDateTime.now().toString());
            facility.setDeleted(false);
            facility.setStatus(true);
            return facilityMapper.toDto(this.facilityRepository.save(facility));
        } catch (Exception exception) {
            log.error("error in facility", exception);
        }
        return null;
    }

    @Override
    public List<TimeSlot> getAvailableSlots(BookingDto bookingDto) {
        Facility facility = facilityRepository.findById(bookingDto.getFacilityId())
            .orElseThrow(() -> new RuntimeException("Facility not found"));

        LocalTime open = LocalTime.parse(facility.getOpeningTime());
        LocalTime close = LocalTime.parse(facility.getClosingTime());

        // Fetch booked slots for the date
        List<Booking> bookings = bookingRepository.findActiveBookings(bookingDto.getFacilityId(),
            bookingDto.getBookingDate().toString());

        // Convert booked slots into a list of TimeSlot objects
        List<TimeSlot> bookedSlots = bookings.stream()
            .map(b -> new TimeSlot(LocalTime.parse(b.getStartTime()),
                LocalTime.parse(b.getEndTime())))
            .collect(Collectors.toList());

        // Generate available slots (default: 1 hour interval)
        List<TimeSlot> timeSlots=generateAvailableSlots(open, close, bookedSlots, Duration.ofHours(1));
        for (TimeSlot timeSlot:timeSlots){
            log.info("time slot"+timeSlot);
        };
        return timeSlots;
    }


    private List<TimeSlot> generateAvailableSlots(LocalTime open, LocalTime close, List<TimeSlot> bookedSlots, Duration interval) {
        List<TimeSlot> available = new ArrayList<>();

        // Sort booked slots by start time (optional)
        bookedSlots.sort(Comparator.comparing(TimeSlot::getStart));

        // Calculate total slots in hours
        long totalHours = Duration.between(open, close).toHours();
        long intervalHours = interval.toHours();
        long totalSlots = totalHours / intervalHours;

        for (int i = 0; i < totalSlots; i++) {
            LocalTime current = open.plusHours(i * intervalHours);
            LocalTime next = current.plus(interval);

            // Filter booked slots that overlap with the current slot
            boolean overlaps = bookedSlots.stream()
                .filter(slot -> !(next.isBefore(slot.getStart()) || current.isAfter(slot.getEnd())))
                .findAny()
                .isPresent();

            if (!overlaps) {
                available.add(new TimeSlot(current, next));
            }
        }

        return available;
    }
}
