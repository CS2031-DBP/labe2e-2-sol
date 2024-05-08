package org.e2e.labe2e02.ride.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RidesByUserDto {
    @NotNull
    private Long id;

    @NotNull
    @Size(min=2, max=255)
    private String originName;

    @NotNull
    @Size(min=2, max=255)
    private String destinationName;

    @NotNull
    @DecimalMin(value="0.1")
    private Double price;

    @NotNull
    private LocalDateTime departureDate;
}