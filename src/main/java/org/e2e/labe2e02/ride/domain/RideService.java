package org.e2e.labe2e02.ride.domain;

import org.e2e.labe2e02.coordinate.domain.Coordinate;
import org.e2e.labe2e02.driver.domain.Driver;
import org.e2e.labe2e02.driver.infrastructure.DriverRepository;
import org.e2e.labe2e02.exceptions.ResourceNotFoundException;
import org.e2e.labe2e02.passenger.domain.Passenger;
import org.e2e.labe2e02.passenger.infrastructure.PassengerRepository;
import org.e2e.labe2e02.ride.dto.RideRequestDto;
import org.e2e.labe2e02.ride.dto.RidesByUserDto;
import org.e2e.labe2e02.ride.infrastructure.RideRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RideService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private ModelMapper modelMapper;

    public RideService() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setImplicitMappingEnabled(false);
        modelMapper.typeMap(RideRequestDto.class, Ride.class).addMappings(mapper -> {
            mapper.map(RideRequestDto::getOriginName, Ride::setOriginName);
            mapper.map(RideRequestDto::getDestinationName, Ride::setDestinationName);
            mapper.map(RideRequestDto::getPrice, Ride::setPrice);
            mapper.map(RideRequestDto::getOriginCoordinates, Ride::setOriginCoordinates);
            mapper.map(RideRequestDto::getDestinationCoordinates, Ride::setDestinationCoordinates);
            mapper.map(RideRequestDto::getPassengerId, (dest, v) -> dest.getPassenger().setId((Long) v));
            mapper.map(RideRequestDto::getDriverId, (dest, v) -> dest.getDriver().setId((Long) v));
        });
    }

    public String createRideDTO(RideRequestDto rideRequestDto) {
        if (rideRequestDto.getPassengerId() == null) {
            throw new ResourceNotFoundException("Passenger id is required");
        }
        if (rideRequestDto.getDriverId() == null) {
            throw new ResourceNotFoundException("Driver id is required");
        }
        Ride newRide = new Ride();
        Passenger passenger = passengerRepository.findById(rideRequestDto.getPassengerId())
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));
        Driver driver = driverRepository.findById(rideRequestDto.getDriverId())
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));
        newRide.setPassenger(passenger);
        newRide.setDriver(driver);
        newRide.setOriginName(rideRequestDto.getOriginName());
        newRide.setDestinationName(rideRequestDto.getDestinationName());
        newRide.setPrice(rideRequestDto.getPrice());
        Coordinate originCoordinate = modelMapper.map(rideRequestDto.getOriginCoordinates(), Coordinate.class);
        newRide.setOriginCoordinates(originCoordinate);
        Coordinate destinationCoordinate = modelMapper.map(rideRequestDto.getDestinationCoordinates(), Coordinate.class);
        newRide.setDestinationCoordinates(destinationCoordinate);
        newRide.setStatus(Status.COMPLETED);
        newRide.setDepartureDate(LocalDateTime.now());
        Ride savedRide = rideRepository.save(newRide);
        return "ride/" + savedRide.getId();
    }

    public void assignRide(Long rideId) {
        Ride ride = rideRepository
                .findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        ride.setStatus(Status.ACCEPTED);
        rideRepository.save(ride);
    }

    public void cancelRide(Long rideId) {
        Ride ride = rideRepository
                .findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        ride.setStatus(Status.CANCELLED);
        rideRepository.save(ride);
    }

    public void updateRideStatus(Long rideId, String status) {
        Ride ride = rideRepository
                .findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        ride.setStatus(Status.valueOf(status));
        rideRepository.save(ride);
    }

    public Page<RidesByUserDto> getRidesByUserDTO(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return rideRepository.findAllByPassengerIdAndStatus(id, Status.COMPLETED, pageable)
                .map(ride -> modelMapper.map(ride, RidesByUserDto.class));
    }
}