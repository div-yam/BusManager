package com.busManager.busmanager.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatAvailability {
    private Integer busRouteId;
    private Integer availableSeats;
}
