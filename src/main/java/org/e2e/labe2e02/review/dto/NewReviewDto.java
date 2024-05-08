package org.e2e.labe2e02.review.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewReviewDto {
    @NotNull
    private String comment;

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    private Integer rating;

    @NotNull
    @Valid
    private Long rideId;

    @NotNull
    private Long targetId;
}