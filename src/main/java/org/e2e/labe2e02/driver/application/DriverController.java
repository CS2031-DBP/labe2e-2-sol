package org.e2e.labe2e02.driver.application;

import org.e2e.labe2e02.driver.domain.Driver;
import org.e2e.labe2e02.driver.domain.DriverService;
import org.e2e.labe2e02.driver.dto.DriverDto;
import org.e2e.labe2e02.driver.dto.NewDriverRequestDto;
import org.e2e.labe2e02.vehicle.domain.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/driver")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @GetMapping("/{id}")
    public ResponseEntity<DriverDto> getDriverDTO(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.getDriverDTO(id));
    }

    @PostMapping()
    public ResponseEntity<Void> saveDriverDTO(@RequestBody NewDriverRequestDto driver) {
        String uri = driverService.saveDriverDTO(driver);
        return ResponseEntity.created(URI.create(uri)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDriverDTO(@PathVariable Long id, @RequestBody DriverDto driver) {
        driverService.updateDriverDTO(id, driver);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/location")
    public ResponseEntity<Void> updateDriverLocation(
            @PathVariable Long id,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude
    ) {
        driverService.updateDriverLocation(id, latitude, longitude);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/car")
    public ResponseEntity<DriverDto> updateDriverCarDTO(@PathVariable Long id, @RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(driverService.updateDriverCarDTO(id, vehicle));
    }


}
