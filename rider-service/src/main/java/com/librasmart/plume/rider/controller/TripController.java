package com.librasmart.plume.rider.controller;

import com.librasmart.plume.rider.util.SortValidator;
import com.librasmart.plume.rider.dto.ApiResponse;
import com.librasmart.plume.rider.dto.TripRequestDto;
import com.librasmart.plume.rider.dto.TripResponseDto;
import com.librasmart.plume.rider.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;
    private static final Set<String> SORTABLE_FIELDS = Set.of("requestedAt", "updatedAt");

    @PostMapping
    public ResponseEntity<ApiResponse<TripResponseDto>> requestTrip(@Valid @RequestBody TripRequestDto tripRequestDto) {
        TripResponseDto tripResponseDto = TripResponseDto.from(tripService.createTrip(tripRequestDto));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tripResponseDto.id())
                .toUri();

        return ResponseEntity.created(location).body(ApiResponse.success("Trip created successfully.", tripResponseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TripResponseDto>> getTrip(@PathVariable UUID tripId) {
        TripResponseDto tripResponseDto = TripResponseDto.from(tripService.getTrip(tripId));
        return ResponseEntity.ok(ApiResponse.success("Trip retrieved successfully.", tripResponseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedModel<TripResponseDto>>> listTrips(
            @PageableDefault(sort = "requestedAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        SortValidator.validate(pageable, SORTABLE_FIELDS);

        Page<TripResponseDto> trips = tripService.listTrips(pageable).map(TripResponseDto::from);
        return ResponseEntity.ok(ApiResponse.success("Trips retrieved successfully.", new PagedModel<>(trips)));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelTrip(@PathVariable UUID tripId) {
        tripService.cancelTrip(tripId);
        return ResponseEntity.ok(ApiResponse.success("Trip cancelled successfully.", null));
    }
}
