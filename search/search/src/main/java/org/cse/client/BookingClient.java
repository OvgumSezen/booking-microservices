package org.cse.client;

import org.cse.response.BookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "booking-service", url = "http://localhost:8082")
public interface BookingClient {
    @GetMapping("/booking/list")
    List<BookingResponse> getBookings();
}
