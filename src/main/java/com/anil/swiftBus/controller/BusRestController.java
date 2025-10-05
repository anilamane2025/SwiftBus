package com.anil.swiftBus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anil.swiftBus.dto.BusDTO;
import com.anil.swiftBus.service.BusService;

@RestController
@RequestMapping("/admin/api")
public class BusRestController {

    private final BusService busService;

    public BusRestController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping("/trip/{tripId}/bus/{busId}/seats")
    public ResponseEntity<BusDTO> getBusSeats(
            @PathVariable Long tripId,
            @PathVariable Long busId) {

        BusDTO bus = busService.getBus(busId);
        bus = busService.getBusWithBookedStatus(bus, tripId);

        if (bus == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(bus);
    }
}
