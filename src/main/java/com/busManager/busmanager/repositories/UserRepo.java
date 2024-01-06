package com.busManager.busmanager.repositories;

import com.busManager.busmanager.data.dto.BusSearchResponse;

import java.util.List;

public interface UserRepo {
    public List<BusSearchResponse> getBuses(String source, String destination, String day);
}
