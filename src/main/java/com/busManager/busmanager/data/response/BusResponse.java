package com.busManager.busmanager.data.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BusResponse {
    String busId;
    Integer distance;
    Integer ETA;
    Date departureTime;
}
