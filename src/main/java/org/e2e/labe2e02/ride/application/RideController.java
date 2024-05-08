package org.e2e.labe2e02.ride.application;


import org.e2e.labe2e02.ride.domain.Ride;
import org.e2e.labe2e02.ride.domain.RideService;
import org.e2e.labe2e02.ride.dto.RideRequestDto;
import org.e2e.labe2e02.ride.dto.RidesByUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/ride")
public class RideController {
    @Autowired
    private RideService rideService;

    @Autowired
    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping()
    public ResponseEntity<Void> passengerBookRide(
            @RequestBody RideRequestDto rideRequestDto
    ) {
        String uri = rideService.createRideDTO(rideRequestDto);
        return ResponseEntity.created(URI.create(uri)).build();
    }

    @PatchMapping("/assign/{rideId}")
    public ResponseEntity<Void> driverAssignRide(@PathVariable Long rideId) {
        rideService.assignRide(rideId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{rideId}")
    public ResponseEntity<Void> cancelRide(@PathVariable Long rideId) {
        rideService.cancelRide(rideId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Page<RidesByUserDto>> getRideByUser(
            @PathVariable Long userId,
            @RequestParam int page,
            @RequestParam int size
    ) {
        Page<RidesByUserDto> response = rideService.getRidesByUserDTO(userId, page, size);
        return ResponseEntity.ok(response);
    }


}