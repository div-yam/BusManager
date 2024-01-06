package com.busManager.busmanager.services;

import com.busManager.busmanager.data.request.AdditionRequest;

public interface AdminService {
    public String add(AdditionRequest additionRequest);
    public String update();
    public String delete();
}
