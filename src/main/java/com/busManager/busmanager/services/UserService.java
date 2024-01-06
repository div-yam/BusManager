package com.busManager.busmanager.services;

import com.busManager.busmanager.data.request.*;
import com.busManager.busmanager.data.response.*;

public interface UserService {
    public SearchResponse search(SearchRequest searchRequest);
    public CheckEligibilityResponse checkEligibility(CheckEligibilityRequest checkEligibilityRequest);
    public HoldResponse hold(HoldRequest holdRequest);
    public BookResponse book(BookRequest bookRequest);
    public CancelResponse cancel(CancelRequest cancelRequest);
}
