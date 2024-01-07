package com.busManager.busmanager.repositories;

import com.busManager.busmanager.data.dto.BusSearchResponse;
import com.busManager.busmanager.data.response.CheckEligibilityResponse;

import java.util.List;

public interface UserRepo {
    public List<BusSearchResponse> getBuses(String source, String destination, String day);
    public CheckEligibilityResponse getEligibilityResponse(Integer busRouteId, String date);
}
