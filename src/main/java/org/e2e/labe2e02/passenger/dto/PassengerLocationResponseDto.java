package org.e2e.labe2e02.passenger.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.e2e.labe2e02.coordinate.dto.CoordinateDto;

@Data
public class PassengerLocationResponseDto {
    @NotNull
    @Valid
    private CoordinateDto coordinate;

    @NotNull
    private String description;

    public PassengerLocationResponseDto(CoordinateDto coordinate, String description) {
        this.coordinate = coordinate;
        this.description = description;
    }

    public PassengerLocationResponseDto() {
    }

    public CoordinateDto getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(CoordinateDto coordinate) {
        this.coordinate = coordinate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLatitude(Double latitude) {
        this.coordinate.setLatitude(latitude);
    }

    public void setLongitude(Double longitude) {
        this.coordinate.setLongitude(longitude);
    }
}