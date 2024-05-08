package org.e2e.labe2e02.vehicle.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VehicleBasicDto {
    @NotNull
    @Size(min = 2, max = 50)
    private String brand;

    @NotNull
    @Size(min = 2, max = 50)
    private String model;

    @NotNull
    @Size(min = 2, max = 50)
    private String licensePlate;

    @NotNull
    @Min(value = 1900)
    @Max(value = 2024)
    private Integer fabricationYear;

    private Integer capacity;
}