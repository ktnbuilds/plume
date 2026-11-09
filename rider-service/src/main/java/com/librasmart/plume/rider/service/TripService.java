package com.librasmart.plume.rider.service;

import com.librasmart.plume.rider.dto.TripRequestDto;
import com.librasmart.plume.rider.entity.Trip;
import com.librasmart.plume.rider.entity.TripStatus;
import com.librasmart.plume.rider.event.TripEventProducer;
import com.librasmart.plume.rider.event.TripRequestedEvent;
import com.librasmart.plume.rider.exception.TripNotFoundException;
import com.librasmart.plume.rider.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final TripEventProducer tripEventProducer;

    public Trip createTrip(TripRequestDto tripRequestDto) {
        Trip trip = Trip.builder()
                .riderId(tripRequestDto.riderId())
                .status(TripStatus.REQUESTED)
                .pickupLocation(tripRequestDto.pickupLocation())
                .dropoffLocation(tripRequestDto.dropoffLocation())
                .build();

        Trip savedTrip = tripRepository.save(trip);

        TripRequestedEvent event = new TripRequestedEvent(
                savedTrip.getId(),
                savedTrip.getRiderId(),
                savedTrip.getPickupLocation(),
                savedTrip.getDropoffLocation(),
                savedTrip.getRequestedAt()
        );

        tripEventProducer.publishTripRequested(event);

        return savedTrip;
    }

    public Trip getTrip(UUID tripId) {
        return tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
    }

    public Page<Trip> listTrips(Pageable pageable) {
        return tripRepository.findAll(pageable);
    }

    @Transactional
    public void cancelTrip(UUID tripId) {
        Trip trip = getTrip(tripId);
        trip.transitionTo(TripStatus.CANCELLED);
    }
}
