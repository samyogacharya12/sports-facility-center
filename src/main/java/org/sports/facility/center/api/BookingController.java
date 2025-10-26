package org.sports.facility.center.api;


import lombok.extern.slf4j.Slf4j;
import org.sports.facility.center.dto.BookingDto;
import org.sports.facility.center.dto.TimeSlot;
import org.sports.facility.center.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class BookingController {


    @Autowired
    private BookingService bookingService;



    @PostMapping("/booking")
    public ResponseEntity<BookingDto> save(@RequestBody BookingDto bookingDto) {
        log.info("save");
        return new ResponseEntity<>(bookingService.save(bookingDto), HttpStatus.OK);
    }

    @PostMapping("/cancel/booking")
    public ResponseEntity<BookingDto> cancel(@RequestBody BookingDto bookingDto) {
        log.info("save");
        return new ResponseEntity<>(bookingService.cancelBooking(bookingDto), HttpStatus.OK);
    }


    @PostMapping("/user/bookings")
    public ResponseEntity<List<BookingDto>> findByUserName(@RequestBody BookingDto bookingDto) {
        log.info("save");
        return new ResponseEntity<>(bookingService.findByUserName(bookingDto), HttpStatus.OK);
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingDto>> findAll() {
        log.info("findAll");
        return new ResponseEntity<>(bookingService.findAll(), HttpStatus.OK);
    }



}
