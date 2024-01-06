package com.busManager.busmanager.services.implementations;

import com.busManager.busmanager.data.request.*;
import com.busManager.busmanager.data.response.*;
import com.busManager.busmanager.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Override
    public SearchResponse search(SearchRequest searchRequest) {
        return null;
    }

    @Override
    public CheckEligibilityResponse checkEligibility(CheckEligibilityRequest checkEligibilityRequest) {
        return null;
    }
    @Override
    public HoldResponse hold(HoldRequest holdRequest) {
        return null;
    }
    @Override
    public BookResponse book(BookRequest bookRequest) {
        return null;
    }
    @Override
    public CancelResponse cancel(CancelRequest cancelRequest) {
        return null;
    }
}
