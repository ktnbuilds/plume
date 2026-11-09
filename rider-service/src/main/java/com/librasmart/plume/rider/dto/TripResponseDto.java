package com.librasmart.plume.rider.dto;

import com.librasmart.plume.rider.entity.Location;
import com.librasmart.plume.rider.entity.Trip;
import com.librasmart.plume.rider.entity.TripStatus;

import java.time.Instant;
import java.util.UUID;

public record TripResponseDto(UUID id, UUID riderId, UUID driverId, TripStatus status, Location pickupLocation, Location dropoffLocation, Instant requestedAt, Instant updatedAt) {

    public static TripResponseDto from(Trip trip) {
        return new TripResponseDto(
                trip.getId(),
                trip.getRiderId(),
                trip.getDriverId(),
                trip.getStatus(),
                trip.getPickupLocation(),
                trip.getDropoffLocation(),
                trip.getRequestedAt(),
                trip.getUpdatedAt()
        );
    }
}
