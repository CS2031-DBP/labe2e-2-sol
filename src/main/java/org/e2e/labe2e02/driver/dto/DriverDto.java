package org.e2e.labe2e02.driver.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.e2e.labe2e02.driver.domain.Category;
import org.e2e.labe2e02.vehicle.dto.VehicleBasicDto;

@Data
public class DriverDto {
    private Long id;
    @NotNull
    private Category category;
    @NotNull
    @Size(min = 2, max = 50)
    private String firstName;
    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;
    @NotNull
    @PositiveOrZero
    private Integer trips;
    @NotNull
    @PositiveOrZero
    private Float avgRating;
    @NotNull
    @Valid
    private VehicleBasicDto vehicle;
}