package com.librasmart.plume.matching.event;

import com.librasmart.plume.matching.entity.Location;

import java.time.Instant;
import java.util.UUID;

public record TripRequestedEvent(
        UUID tripId,
        UUID riderId,
        Location pickupLocation,
        Location dropoffLocation,
        Instant requestedAt
) {}
