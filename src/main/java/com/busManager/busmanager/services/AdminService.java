package com.busManager.busmanager.services;

import com.busManager.busmanager.data.request.AddBusRequest;
import com.busManager.busmanager.data.request.DeleteBusRequest;
import com.busManager.busmanager.data.request.UpdateBusRequest;

public interface AdminService {
    public String add(AddBusRequest addBusRequest);
    public String update(UpdateBusRequest updateBusRequest);
    public String delete(DeleteBusRequest deleteBusRequest);
}
