package com.busManager.busmanager.data.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
public class AdditionRequest {
    String busName;
    Integer totalSeats;
    String source;
    String destination;
    Time departureTime;
    Integer distance;
}
