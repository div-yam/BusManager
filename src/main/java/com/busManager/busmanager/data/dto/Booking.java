package com.busManager.busmanager.data.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class Booking {
    private Integer bookingId;
    private Integer userId;
    private Integer busRouteId;
    private Integer seatNumber;
    private Date bookingDate;
    private String status;
}
