package com.busManager.busmanager.data.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CheckEligibilityRequest {
    private Integer busRouteId;
    private Date departureDate;
}
