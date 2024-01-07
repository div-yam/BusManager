package com.busManager.busmanager.repositories;

import com.busManager.busmanager.data.dto.Bus;
import com.busManager.busmanager.data.request.AddBusRequest;

public interface AdminRepo {
    public Integer addBus(String busName, Integer totalSeats);

    public Integer addRoute(AddBusRequest addBusRequest);

    public Integer addBusRoute(Integer busId, Integer routeId);
}
