package com.busManager.busmanager.data.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
public class HoldRequest {
    private Integer busRouteId;
    private String source;
    private String destination;
    private Date departureDate;
    private Integer userId;
}
