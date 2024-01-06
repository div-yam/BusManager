package com.busManager.busmanager.services;

import com.busManager.busmanager.data.request.SearchRequest;
import com.busManager.busmanager.data.response.SearchResponse;

public interface UserService {
    public SearchResponse search(SearchRequest searchRequest);
}
