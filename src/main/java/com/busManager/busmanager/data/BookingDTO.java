package com.busManager.busmanager.data;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class BookingDTO {
    private Integer bookingId;
    private Integer userId;
    private Integer busRouteId;
    private Integer seatNumber;
    private Date bookingDate;
    private String status;
}
