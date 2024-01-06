package com.busManager.busmanager.data.response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckEligibilityResponse {
    Integer numberOfSeats;
    String occupancyColour;
}
