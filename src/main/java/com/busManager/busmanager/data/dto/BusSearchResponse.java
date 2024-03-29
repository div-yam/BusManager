package com.busManager.busmanager.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusSearchResponse {

    @JsonProperty("bus_name")
    String busName;

    Integer distance;
    @JsonProperty("departure_time")
    Time departureTime;

    @JsonProperty("bus_route_id")
    Integer busRouteId;
    Time ETA;
}
