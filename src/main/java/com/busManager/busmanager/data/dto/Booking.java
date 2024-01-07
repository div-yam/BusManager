package com.busManager.busmanager.data.dto;

import com.busManager.busmanager.data.BookingStatus;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;


@Getter
@Setter
public class Booking {
    private Integer bookingId;
    private Integer userId;
    private Integer busRouteId;
    private Integer seatNumber;
    private Date bookingDate;
    private BookingStatus status;
    private Timestamp timestamp;
    private Date dateOfTravel;
}
