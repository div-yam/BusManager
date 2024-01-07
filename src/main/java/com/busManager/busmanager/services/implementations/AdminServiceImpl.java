package com.busManager.busmanager.services.implementations;

import com.busManager.busmanager.data.request.AddBusRequest;
import com.busManager.busmanager.repositories.AdminRepo;
import com.busManager.busmanager.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepo adminRepo;
    @Override
    public String add(AddBusRequest addBusRequest) {
        //Add bus data;
        Integer busId=adminRepo.addBus(addBusRequest.getBusName(),addBusRequest.getTotalSeats());

        Integer routeId= adminRepo.addRoute(addBusRequest);


        // Add bus_routes;
        Integer busRouteId=adminRepo.addBusRoute(busId,routeId);

        //Handle Seat Availability

        // Handle bus schedule

return null;


    }

    @Override
    public String update() {
        return null;
    }

    @Override
    public String delete() {
        return null;
    }
}
