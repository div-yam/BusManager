package com.busManager.busmanager.repositories;

import com.busManager.busmanager.data.dto.Booking;
import com.busManager.busmanager.data.dto.BusSearchResponse;
import com.busManager.busmanager.data.response.CheckEligibilityResponse;


import java.sql.Date;
import java.util.List;

public interface UserRepo {
    public List<BusSearchResponse> getBuses(String source, String destination, String day);
    public CheckEligibilityResponse getEligibilityResponse(Integer busRouteId, Date date);

    //insert booking hold user_id, bus_route_id, date_of_travel, seat_number, status
    public Integer holdBooking(Integer userId, Integer busRouteId, Date dateOfTravel, int seatNumber);
    //update seat availabilty bus_route_id = 1 AND date = '2024-01-10'
    public boolean decreaseAvailableSeatCount(Integer busRouteId, Date dateOfTravel);
    //get seat availability number list //bus_route_id = 1
    //AND date_of_travel = '2024-01-10'
    public List<Integer> getSeatsBookedOrHold(Date dateOfTravel, Integer busRouteId);
    //update booking book booking_id = 1
    //AND user_id = 1
    public boolean updateBookingStatus(Integer bookingId, Integer userId);
    public boolean cancelBooking(Integer bookingId, Integer userId);
    public List<Booking> getBooking(Integer bookingId);
    public boolean increaseAvailableSeatCount(Integer busRouteId, Date dateOfTravel);
}
