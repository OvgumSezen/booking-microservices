package org.cse.controller;

import lombok.RequiredArgsConstructor;
import org.cse.response.ApartmentResponse;
import org.cse.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<List<ApartmentResponse>> search(@RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to) {
        List<ApartmentResponse> availableApartments = searchService.search(from, to);
        return new ResponseEntity<>(availableApartments, HttpStatus.OK);
    }
}
