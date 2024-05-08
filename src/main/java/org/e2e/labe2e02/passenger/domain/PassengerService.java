package org.e2e.labe2e02.passenger.domain;

import org.e2e.labe2e02.coordinate.domain.Coordinate;
import org.e2e.labe2e02.coordinate.dto.CoordinateDto;
import org.e2e.labe2e02.coordinate.infrastructure.CoordinateRepository;
import org.e2e.labe2e02.exceptions.ResourceNotFoundException;
import org.e2e.labe2e02.exceptions.UniqueResourceAlreadyExist;
import org.e2e.labe2e02.passenger.dto.PassengerLocationResponseDto;
import org.e2e.labe2e02.passenger.dto.PassengerRequestDto;
import org.e2e.labe2e02.passenger.dto.PassengerResponseDto;
import org.e2e.labe2e02.passenger.infrastructure.PassengerRepository;
import org.e2e.labe2e02.user_locations.domain.UserLocation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private CoordinateRepository coordinateRepository;

    @Autowired
    private ModelMapper modelMapper;


    //#1
    public String createPassengerDTO(PassengerRequestDto passengerRequestDto) {
        if (passengerRepository.findByEmail(passengerRequestDto.getEmail()).isPresent()) {
            throw new UniqueResourceAlreadyExist("Passenger already exists");
        }
        Passenger passenger = modelMapper.map(passengerRequestDto, Passenger.class);

        Passenger savedPassenger = passengerRepository.save(passenger);
        return "/passenger/" + savedPassenger.getId();
    }

    //#2
    public PassengerResponseDto getPassengerDTO(Long id) {
        Passenger passenger = passengerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));

        return modelMapper.map(passenger, PassengerResponseDto.class);
    }

    //--------------------------------------------------------------------------------

    public void deletePassenger(Long id) {
        //if location is empty, throw exception
        passengerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));
        passengerRepository.deleteById(id);
    }

    //--------------------------------------------------------------------------------

    //#3
    public void addPassengerPlaceDTO(Long id, PassengerLocationResponseDto passengerLocationResponseDto) {
        Passenger passenger = passengerRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        Coordinate coordinate = modelMapper.map(passengerLocationResponseDto.getCoordinate(), Coordinate.class);
        coordinateRepository.save(coordinate);

        passenger.addCoordinate(coordinate, passengerLocationResponseDto.getDescription());
        passengerRepository.save(passenger);
    }

    //--------------------------------------------------------------------------------

    public void deletePassengerPlace(Long id, Long coordinateId) {
        Passenger passenger = passengerRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        Coordinate coordinate = coordinateRepository
                .findById(coordinateId)
                .orElseThrow(() -> new RuntimeException("Coordinate not found"));

        passenger.removeCoordinate(coordinate);
        passengerRepository.save(passenger);
    }

    //--------------------------------------------------------------------------------

    //#4
    public List<PassengerLocationResponseDto> getPassengerPlacesDTO(Long id) {
        Passenger passenger = passengerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));

        List<PassengerLocationResponseDto> passengerLocationResponseDtos = new ArrayList<>();

        for (UserLocation userLocation : passenger.getCoordinates()) {
            CoordinateDto coordinateDto = modelMapper.map(userLocation.getCoordinate(), CoordinateDto.class);
            PassengerLocationResponseDto passengerLocationResponseDto = new PassengerLocationResponseDto(coordinateDto, userLocation.getDescription());
            passengerLocationResponseDtos.add(passengerLocationResponseDto);
        }

        return passengerLocationResponseDtos;
    }
}
