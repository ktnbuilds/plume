package com.librasmart.plume.rider.dto;

import com.librasmart.plume.rider.entity.Location;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TripRequestDto (
        @NotNull UUID riderId,
        @NotNull @Valid Location pickupLocation,
        @NotNull @Valid Location dropoffLocation
) { }
