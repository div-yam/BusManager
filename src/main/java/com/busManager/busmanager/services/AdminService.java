package com.busManager.busmanager.services;

import com.busManager.busmanager.data.request.AddBusRequest;
import com.busManager.busmanager.data.request.DeleteBusRequest;

public interface AdminService {
    public String add(AddBusRequest addBusRequest);
    public String update();
    public String delete(DeleteBusRequest deleteBusRequest);
}
