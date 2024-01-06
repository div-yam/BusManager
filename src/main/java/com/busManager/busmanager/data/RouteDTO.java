package com.busManager.busmanager.data;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
public class RouteDTO {
    private Integer routeId;
    private String source;
    private String destination;
    private Float distance;
    private Time eta;
}
