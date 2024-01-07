package com.busManager.busmanager.data.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
public class UpdateBusRequest {
    String busName;
    Time newDepartureTime;
}
