package com.busManager.busmanager.services.implementations;

import com.busManager.busmanager.data.request.SearchRequest;
import com.busManager.busmanager.data.response.SearchResponse;
import com.busManager.busmanager.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Override
    public SearchResponse search(SearchRequest searchRequest) {
        return null;
    }
}
