package com.busManager.busmanager.services.implementations;

import com.busManager.busmanager.data.dto.Booking;
import com.busManager.busmanager.data.dto.BusSearchResponse;
import com.busManager.busmanager.data.request.*;
import com.busManager.busmanager.data.response.*;
import com.busManager.busmanager.data.request.SearchRequest;
import com.busManager.busmanager.data.response.SearchResponse;
import com.busManager.busmanager.repositories.UserRepo;
import com.busManager.busmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
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

        CheckEligibilityResponse checkEligibilityResponse =
                userRepo.getEligibilityResponse(checkEligibilityRequest.getBusRouteId(), checkEligibilityRequest.getDepartureDate());

        return checkEligibilityResponse;
    }
    @Override
    public HoldResponse hold(HoldRequest holdRequest) {
        HoldResponse holdResponse= new HoldResponse();

        //check-elegibility
        CheckEligibilityRequest checkEligibilityRequest=new CheckEligibilityRequest();
        checkEligibilityRequest.setBusRouteId(holdRequest.getBusRouteId());
        checkEligibilityRequest.setDepartureDate(holdRequest.getDepartureDate());
        CheckEligibilityResponse checkEligibilityResponse= checkEligibility(checkEligibilityRequest);
        if(Objects.isNull(checkEligibilityResponse) || checkEligibilityResponse.getNumberOfSeats()<1){
            return holdResponse;
        }
        // get list of seat numbers which are booked ||  hold in last 5 minutes

        List<Integer> seats= userRepo.getSeatsBookedOrHold(holdRequest.getDepartureDate(),holdRequest.getBusRouteId());
        Set<Integer> seatSet= new HashSet<>(seats);
        int seatNumber=0;
        for(int i=1;i<=checkEligibilityResponse.getTotalNumberOfSeats();i++){
            if(!seatSet.contains(i)) {
                seatNumber = i;
                break;
            }
        }

        if(seatNumber==0)
            return holdResponse;

        // make a booking with hold and update seat availability
        Integer bookingId= userRepo.holdBooking(holdRequest.getUserId(),holdRequest.getBusRouteId(),holdRequest.getDepartureDate(),seatNumber);
        if(Objects.isNull(bookingId)) {
            throw new RuntimeException();
        }

        boolean status= userRepo.decreaseAvailableSeatCount(holdRequest.getBusRouteId(), holdRequest.getDepartureDate());
        if(!status) {
            throw new RuntimeException();
        }


        //return booking id and seat number
        holdResponse.setAvailability(true);
        holdResponse.setBookingId(bookingId);
        holdResponse.setSeatNumber(seatNumber);

        return holdResponse;
    }
    @Override
    public BookResponse book(BookRequest bookRequest) {
        return new BookResponse(userRepo.updateBookingStatus(bookRequest.getBookingId(), bookRequest.getUserId()));
    }
    @Override
    public CancelResponse cancel(CancelRequest cancelRequest) {
        CancelResponse cancelResponse = new CancelResponse();
        cancelResponse.setBookingCanceled(false);
        //check bookingID and userId and booking date
        List<Booking> bookings = userRepo.getBooking(cancelRequest.getBookingId());
        if (Objects.isNull(bookings) || 1 != bookings.size())
            return cancelResponse;
        //update status of booking
        boolean cancelSuccess = userRepo.cancelBooking(cancelRequest.getBookingId(), cancelRequest.getUserId());
        if (!cancelSuccess)
            throw new RuntimeException();

        boolean updateSeat = userRepo.increaseAvailableSeatCount(bookings.get(0).getBusRouteId(), bookings.get(0).getDateOfTravel());
        cancelResponse.setBookingCanceled(true);

        return cancelResponse;
    }
}
