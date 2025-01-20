package org.cse.controller;

import lombok.RequiredArgsConstructor;
import org.cse.response.ApartmentResponse;
import org.cse.service.ApartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apartment")
@RequiredArgsConstructor
public class ApartmentController {
    private final ApartmentService apartmentService;

    @PostMapping("/add")
    public ResponseEntity<ApartmentResponse> add(@RequestParam("name") String name,
                                                 @RequestParam("address") String address,
                                                 @RequestParam("noiseLevel") Integer noiseLevel,
                                                 @RequestParam("floor") Integer floor) {
        ApartmentResponse apartmentResponse = apartmentService.add(name, address, noiseLevel, floor);
        return new ResponseEntity<>(apartmentResponse, HttpStatus.CREATED);
    }

    @PostMapping("/remove")
    public ResponseEntity<ApartmentResponse> remove(@RequestParam("id") Integer id) {
        ApartmentResponse apartmentResponse = apartmentService.remove(id);
        return new ResponseEntity<>(apartmentResponse, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ApartmentResponse>> list() {
        List<ApartmentResponse> apartmentResponses = apartmentService.list();
        return new ResponseEntity<>(apartmentResponses, HttpStatus.OK);
    }
}
