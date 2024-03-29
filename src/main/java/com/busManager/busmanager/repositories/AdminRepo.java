package com.busManager.busmanager.repositories;

import com.busManager.busmanager.data.request.AddBusRequest;
import com.busManager.busmanager.data.request.UpdateBusRequest;

public interface AdminRepo {
    public Integer addBus(String busName, Integer totalSeats);
    public Integer addRoute(AddBusRequest addBusRequest);
    public Integer addBusRoute(Integer busId, Integer routeId);
    public void addSeatAvailability(Integer weekDay, Integer busRouteId, Integer totalNumberOfSeats);
    public Integer addBusSchedule(Integer busRoutedId, String weekDay);
    public boolean deleteBus(String busId);
    public boolean updateBus(UpdateBusRequest updateBusRequest);
}
