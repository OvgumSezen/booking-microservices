package org.cse.client;


import org.cse.response.ApartmentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "apartment-service", url = "${apartment.service.url}")
public interface ApartmentClient {
    @GetMapping("/apartment/list")
    List<ApartmentResponse> getAllApartments();
}
