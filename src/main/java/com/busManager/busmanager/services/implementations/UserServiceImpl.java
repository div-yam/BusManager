package com.busManager.busmanager.services.implementations;

import com.busManager.busmanager.data.dto.BusSearchResponse;
import com.busManager.busmanager.data.request.*;
import com.busManager.busmanager.data.response.*;
import com.busManager.busmanager.data.request.SearchRequest;
import com.busManager.busmanager.data.response.SearchResponse;
import com.busManager.busmanager.repositories.UserRepo;
import com.busManager.busmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    @Override
    public SearchResponse search(SearchRequest searchRequest) {
        //make request for repo
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(searchRequest.getDate());

        // Get the day of the week as an integer (1 = Sunday, 2 = Monday, ..., 7 = Saturday)
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Convert the integer to a day name
        String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        String weekday = daysOfWeek[dayOfWeek - 1];


        List<BusSearchResponse> busSearchResponses = userRepo.getBuses(searchRequest.getSource(), searchRequest.getDestination(), weekday);
        return new SearchResponse(busSearchResponses);

    }
    @Override
    public CheckEligibilityResponse checkEligibility(CheckEligibilityRequest checkEligibilityRequest) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkEligibilityRequest.getDepartureDate());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = year+"-"+month+"-"+day;
        CheckEligibilityResponse checkEligibilityResponse =
                userRepo.getEligibilityResponse(checkEligibilityRequest.getBusRouteId(), date);

        return checkEligibilityResponse;
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
