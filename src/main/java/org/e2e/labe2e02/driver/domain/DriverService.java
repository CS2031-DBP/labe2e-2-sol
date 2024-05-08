package org.e2e.labe2e02.driver.domain;

import org.e2e.labe2e02.coordinate.domain.Coordinate;
import org.e2e.labe2e02.coordinate.infrastructure.CoordinateRepository;
import org.e2e.labe2e02.driver.dto.DriverDto;
import org.e2e.labe2e02.driver.dto.NewDriverRequestDto;
import org.e2e.labe2e02.driver.infrastructure.DriverRepository;
import org.e2e.labe2e02.exceptions.ResourceNotFoundException;
import org.e2e.labe2e02.exceptions.UniqueResourceAlreadyExist;
import org.e2e.labe2e02.vehicle.domain.Vehicle;
import org.e2e.labe2e02.vehicle.dto.VehicleBasicDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CoordinateRepository coordinateRepository;

    @Autowired
    private ModelMapper modelMapper;

    public DriverDto getDriverDTO(Long id) {

        Driver driver = driverRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        DriverDto driverDto = modelMapper.map(driver, DriverDto.class);
        driverDto.setVehicle(modelMapper.map(driver.getVehicle(), VehicleBasicDto.class));
        return driverDto;
    }

    public String saveDriverDTO(NewDriverRequestDto driver) {
        if (driverRepository.findByEmail(driver.getEmail()).isPresent()) {
            throw new UniqueResourceAlreadyExist("Driver already exists");
        }

        Driver newDriver = modelMapper.map(driver, Driver.class);
        newDriver.setVehicle(driver.getVehicle());
        Driver savedDriver = driverRepository.save(newDriver);
        return "/driver/" + savedDriver.getId();
    }

    public void deleteDriver(Long id) {
        driverRepository.deleteById(id);
    }

    public void updateDriverDTO(Long id, DriverDto driver) {
        Driver existingDriver = driverRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        existingDriver.setFirstName(driver.getFirstName());
        existingDriver.setLastName(driver.getLastName());
        existingDriver.setTrips(driver.getTrips());
        existingDriver.setAvgRating(driver.getAvgRating());
        existingDriver.setCategory(driver.getCategory());
        driverRepository.save(existingDriver);
    }

    public void updateDriverLocation(Long id, Double latitude, Double longitude) {
        Driver driver = driverRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(latitude);
        coordinate.setLongitude(longitude);
        coordinateRepository.save(coordinate);
        driver.setCoordinate(coordinate);
        driverRepository.save(driver);
    }

    public DriverDto updateDriverCarDTO(Long id, Vehicle vehicle) {
        Driver driver = driverRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));
        driver.setVehicle(vehicle);
        driverRepository.save(driver);

        DriverDto driverDto = modelMapper.map(driver, DriverDto.class);
        VehicleBasicDto vehicleBasicDto = modelMapper.map(vehicle, VehicleBasicDto.class);
        driverDto.setVehicle(vehicleBasicDto);
        return driverDto;
    }
}