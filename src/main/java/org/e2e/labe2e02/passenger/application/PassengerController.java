package org.e2e.labe2e02.passenger.application;

import org.e2e.labe2e02.coordinate.domain.Coordinate;
import org.e2e.labe2e02.passenger.domain.Passenger;
import org.e2e.labe2e02.passenger.domain.PassengerService;
import org.e2e.labe2e02.passenger.dto.PassengerResponseDto;
import org.e2e.labe2e02.passenger.dto.PassengerRequestDto;
import org.e2e.labe2e02.passenger.dto.PassengerLocationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @PostMapping("")
    ResponseEntity<Void> createPassenger(@RequestBody PassengerRequestDto passengerRequestDto) {
         String uri = passengerService.createPassengerDTO(passengerRequestDto);
        return ResponseEntity.created(URI.create(uri)).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<PassengerResponseDto> getPassengerDTO(@PathVariable Long id) {
        PassengerResponseDto passengerResponseDto = passengerService.getPassengerDTO(id);
        return ResponseEntity.ok(passengerResponseDto);
    }

    @PatchMapping("/{id}/places")
    ResponseEntity<Void> addPlaceDTO(@RequestBody PassengerLocationResponseDto passengerLocationResponseDto, @PathVariable Long id) {
        passengerService.addPassengerPlaceDTO(id, passengerLocationResponseDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/places")
    ResponseEntity<List<PassengerLocationResponseDto>> getPlacesDTO(@PathVariable Long id) {
        List<PassengerLocationResponseDto> passengerLocationResponseDtos = passengerService.getPassengerPlacesDTO(id);
        return ResponseEntity.ok(passengerLocationResponseDtos);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/places/{coordinateId}")
    ResponseEntity<Void> deletePlace(@PathVariable Long id, @PathVariable Long coordinateId) {
        passengerService.deletePassengerPlace(id, coordinateId);
        return ResponseEntity.noContent().build();
    }
}