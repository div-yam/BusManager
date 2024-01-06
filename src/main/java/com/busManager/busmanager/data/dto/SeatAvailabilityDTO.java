package com.busManager.busmanager.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatAvailabilityDTO {
    private Integer busRouteId;
    private Integer availableSeats;
}
