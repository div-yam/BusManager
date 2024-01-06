package com.busManager.busmanager.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatAvailabilityDTO {
    private Integer busRouteId;
    private Integer availableSeats;
}
