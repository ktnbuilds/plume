package com.librasmart.plume.rider.event;

import com.librasmart.plume.rider.entity.Location;

import java.time.Instant;
import java.util.UUID;

public record TripRequestedEvent(
        UUID tripId,
        UUID riderId,
        Location pickupLocation,
        Location dropoffLocation,
        Instant requestedAt
) {}