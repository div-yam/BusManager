package com.busManager.busmanager.repositories;

import com.busManager.busmanager.data.dto.Booking;
import com.busManager.busmanager.data.dto.BusSearchResponse;
import com.busManager.busmanager.data.response.CheckEligibilityResponse;


import java.sql.Date;
import java.util.List;

public interface UserRepo {
    public List<BusSearchResponse> getBuses(String source, String destination, String day);
    public CheckEligibilityResponse getEligibilityResponse(Integer busRouteId, Date date);
    public Integer holdBooking(Integer userId, Integer busRouteId, Date dateOfTravel, int seatNumber);
    public boolean decreaseAvailableSeatCount(Integer busRouteId, Date dateOfTravel);
    public List<Integer> getSeatsBookedOrHold(Date dateOfTravel, Integer busRouteId);
    public boolean updateBookingStatus(Integer bookingId, Integer userId);
    public boolean cancelBooking(Integer bookingId, Integer userId);
    public List<Booking> getBooking(Integer bookingId);
    public boolean increaseAvailableSeatCount(Integer busRouteId, Date dateOfTravel);
}
