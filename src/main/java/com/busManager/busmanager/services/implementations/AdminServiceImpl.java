package com.busManager.busmanager.services.implementations;

import com.busManager.busmanager.data.enums.WeekDays;
import com.busManager.busmanager.data.request.AddBusRequest;
import com.busManager.busmanager.data.request.DeleteBusRequest;
import com.busManager.busmanager.data.request.UpdateBusRequest;
import com.busManager.busmanager.repositories.AdminRepo;
import com.busManager.busmanager.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepo adminRepo;
    @Override
    public String add(AddBusRequest addBusRequest) {
        //Add bus data;
        Integer busId = adminRepo.addBus(addBusRequest.getBusName(),addBusRequest.getTotalSeats());
        //Add routeId
        Integer routeId = adminRepo.addRoute(addBusRequest);
        // Add bus_routes;
        Integer busRouteId=adminRepo.addBusRoute(busId,routeId);
        //Handle Seat Availability
        List<WeekDays> weekDaysList = addBusRequest.getWeekDays();
        Integer[] weekDaysArray = new Integer[weekDaysList.size()];
        for (WeekDays weekDay : weekDaysList) {
           adminRepo.addSeatAvailability(weekDay.index,
                    busRouteId, addBusRequest.getTotalSeats());
        }
        // Handle bus schedule
        for (WeekDays weekDay :addBusRequest.getWeekDays()) {
            Integer busScheduleId = adminRepo.addBusSchedule(busRouteId, weekDay.value);
        }
        return null;
    }

    @Override
    public String update(UpdateBusRequest updateBusRequest) {
        boolean updateBusResponse = adminRepo.updateBus(updateBusRequest);
        return null;
    }

    @Override
    public String delete(DeleteBusRequest deleteBusRequest) {
        boolean deleteBusResponse = adminRepo.deleteBus(deleteBusRequest.getBusName());
        return null;
    }
}
