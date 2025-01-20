package org.cse.controller;

import lombok.RequiredArgsConstructor;
import org.cse.response.BookingResponse;
import org.cse.service.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/add")
    public ResponseEntity<BookingResponse> add(@RequestParam("apartment") Integer apartment,
                                               @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                               @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
                                               @RequestParam("who") String who) {
        BookingResponse bookingResponse = bookingService.add(apartment, from, to, who);
        return new ResponseEntity<>(bookingResponse, HttpStatus.CREATED);
    }

    @PostMapping("/cancel")
    public ResponseEntity<BookingResponse> cancel(@RequestParam("id") Integer id) {
        BookingResponse bookingResponse = bookingService.cancel(id);
        return new ResponseEntity<>(bookingResponse, HttpStatus.OK);
    }

    @PostMapping("/change")
    public ResponseEntity<BookingResponse> change(@RequestParam("id") Integer id,
                                                  @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                                  @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        BookingResponse bookingResponse = bookingService.change(id, from, to);
        return new ResponseEntity<>(bookingResponse, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BookingResponse>> list() {
        List<BookingResponse> bookingResponses = bookingService.list();
        return new ResponseEntity<>(bookingResponses, HttpStatus.OK);
    }
}
