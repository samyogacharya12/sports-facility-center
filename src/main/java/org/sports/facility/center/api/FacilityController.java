package org.sports.facility.center.api;

import lombok.extern.slf4j.Slf4j;
import org.sports.facility.center.dto.BookingDto;
import org.sports.facility.center.dto.FacilityDto;
import org.sports.facility.center.dto.TimeSlot;
import org.sports.facility.center.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class FacilityController {
    @Autowired
    private FacilityService facilityService;


    @PostMapping("/facilities")
    public ResponseEntity<FacilityDto> save(@RequestBody FacilityDto facilityDto) {
        log.info("FacilityController save()=>");
        return new ResponseEntity<>(facilityService.save(facilityDto), HttpStatus.OK);
    }

    @GetMapping("/facilities")
    public ResponseEntity<List<FacilityDto>> findAll() {
        log.info("FacilityController findAll()=>");
        return new ResponseEntity<>(facilityService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/facilities/available-slots")
    public ResponseEntity<List<FacilityDto>> findByBookingDate(@RequestBody BookingDto bookingDto) {
        log.info("FacilityController findByBookingDate()=>");
        return new ResponseEntity<>(facilityService.findByBookingDate(bookingDto), HttpStatus.OK);
    }

    @PostMapping("/available-slots")
    public ResponseEntity<List<TimeSlot>> getAvailableSlots(@RequestBody BookingDto bookingDto) {
        List<TimeSlot> slots = facilityService.getAvailableSlots(bookingDto);
        return ResponseEntity.ok(slots);
    }


}
