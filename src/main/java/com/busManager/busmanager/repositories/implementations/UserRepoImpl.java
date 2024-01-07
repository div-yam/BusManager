package com.busManager.busmanager.repositories.implementations;

import com.busManager.busmanager.data.BookingStatus;
import com.busManager.busmanager.data.dto.Booking;
import com.busManager.busmanager.data.dto.BusSearchResponse;
import com.busManager.busmanager.data.response.CheckEligibilityResponse;
import com.busManager.busmanager.exceptions.BookException;
import com.busManager.busmanager.exceptions.CheckEligibilityException;
import com.busManager.busmanager.exceptions.HoldBookingException;
import com.busManager.busmanager.exceptions.SearchBusException;
import com.busManager.busmanager.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.sql.*;

import java.util.List;
import java.util.Objects;

@Repository
public class UserRepoImpl implements UserRepo {

    private static final String GET_BUSES = "SELECT\n" +
            "    buses.bus_name,\n" +
            "    routes.distance,\n" +
            "    routes.departure_time,\n" +
            "    bus_routes.bus_route_id\n" +
            "FROM\n" +
            "    buses\n" +
            "JOIN\n" +
            "    bus_routes ON buses.bus_id = bus_routes.bus_id\n" +
            "JOIN\n" +
            "    routes ON bus_routes.route_id = routes.route_id\n" +
            "JOIN\n" +
            "    bus_schedule ON bus_routes.bus_route_id = bus_schedule.bus_route_id\n" +
            "WHERE\n" +
            "    routes.source = ?\n" +
            "    AND routes.destination = ?\n" +
            "    AND bus_schedule.active = TRUE\n" +
            "   AND bus_schedule.day_of_week = ?;";

    //Todo: Add constant to check status of booking

    private static final String GET_ELIGIBILITY_RESPONSE = "select seats_available , total_seats from seat_availability where bus_route_id=? and date = ?;";

    private static final String HOLD_BOOKING="INSERT INTO bookings (user_id, bus_route_id, date_of_travel, seat_number, status)\n" +
            "VALUES (?, ?, ?, ?, 'HOLD');";

    private static final String DECREASE_SEAT_COUNT ="UPDATE seat_availability\n" +
            "SET seats_available = seats_available - 1\n" +
            "WHERE bus_route_id = ? AND date = ?;";

    private static final String GET_BOOKED_SEATS="SELECT seat_number\n" +
            "FROM bookings\n" +
            "WHERE bus_route_id = ?\n" +
            "AND date_of_travel = ?\n" +
            "AND (status = 'BOOK' OR (status = 'HOLD' AND time_of_booking >= NOW() - INTERVAL '5 minutes' ));";

    private static final String UPDATE_BOOKING="UPDATE bookings\n" +
            "SET status = 'BOOK'\n" +
            "WHERE booking_id = ?\n" +
            "AND user_id = ?\n" +
            "AND status = 'HOLD'\n" +
            "AND time_of_booking >= NOW() - INTERVAL '5 minutes';";

    private static final String BOOKING_CANCEL= "UPDATE bookings\n" +
            "SET status = 'CANCEL'\n" +
            "WHERE booking_id = ? AND user_id = ?\n" +
            "AND status = 'BOOK';";

    private static final String GET_BOOKING= "Select * from bookings WHERE booking_id=?";

    private static final String INCREASE_SEAT_COUNT ="UPDATE seat_availability\n" +
            "SET seats_available = seats_available + 1\n" +
            "WHERE bus_route_id = ? AND date = ?;";
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<BusSearchResponse> getBuses(String source, String destination, String day) {
        try {
            return jdbcTemplate.query(GET_BUSES, new Object[]{source, destination, day}, new RowMapper<BusSearchResponse>() {
                @Override
                public BusSearchResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                    BusSearchResponse busSearchResponse= new BusSearchResponse();
                    busSearchResponse.setBusName(rs.getString("bus_name"));
                    busSearchResponse.setDistance(rs.getInt("distance"));
                    busSearchResponse.setDepartureTime(rs.getTime("departure_time"));
                    busSearchResponse.setBusRouteId(rs.getInt("bus_route_id"));
                    return busSearchResponse;
                }});
        } catch (Exception e) {
            throw new SearchBusException("Error while searching buses");
        }
    }

    @Override
    public CheckEligibilityResponse getEligibilityResponse(Integer busRouteId, Date date) {
        try {
            return jdbcTemplate.queryForObject(GET_ELIGIBILITY_RESPONSE, new Object[]{busRouteId, date}, new RowMapper<CheckEligibilityResponse>() {
                @Override
                public CheckEligibilityResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                    CheckEligibilityResponse checkEligibilityResponse = new CheckEligibilityResponse();
                    checkEligibilityResponse.setNumberOfSeats(rs.getInt("seats_available"));
                    checkEligibilityResponse.setTotalNumberOfSeats(rs.getInt("total_seats"));
                    return checkEligibilityResponse;
                }
            });
        } catch (Exception e) {
            throw new CheckEligibilityException("Error while checking eligibility");
        }

    }

    @Override
    public Integer holdBooking(Integer userId, Integer busRouteId, Date dateOfTravel, int seatNumber) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(HOLD_BOOKING, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setInt(2, busRouteId);
                ps.setDate(3,dateOfTravel);
                ps.setInt(4,seatNumber);
                return ps;
            }, keyHolder);
            return (Integer) Objects.requireNonNull(keyHolder.getKeys()).get("BOOKING_ID");
        } catch (Exception e) {
            throw new HoldBookingException("Error while holding booking");
        }

    }

    @Override
    public boolean decreaseAvailableSeatCount(Integer busRouteId, Date dateOfTravel) {
        try {
            return jdbcTemplate.update(DECREASE_SEAT_COUNT, busRouteId, dateOfTravel) == 1;
        } catch (Exception e) {
            throw new BookException("Error while decreasing seats");
        }

    }

    @Override
    public List getSeatsBookedOrHold(Date dateOfTravel, Integer busRouteID) {
        try {
            return jdbcTemplate.query(GET_BOOKED_SEATS, new Object[]{busRouteID, dateOfTravel},
                    new RowMapper<Integer>() {
                        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                            return rs.getInt("seat_number");
                        }
                    }
            );
        } catch (Exception e) {
            throw new BookException("Error while getting seats");
        }

    }

    @Override
    public boolean updateBookingStatus(Integer bookingId, Integer userId) {
        try {
            return jdbcTemplate.update(UPDATE_BOOKING, bookingId, userId) == 1;
        } catch (Exception e) {
            throw new BookException("Error while updating booking status");
        }

    }

    @Override
    public boolean cancelBooking(Integer bookingId, Integer userId) {
        try {
            return jdbcTemplate.update(BOOKING_CANCEL,bookingId,userId) > 0;
        } catch (Exception e) {
            throw new BookException("Error while cancelling booking status");
        }
    }

    @Override
    public List<Booking> getBooking(Integer bookingId) {
        try {
            return jdbcTemplate.query(GET_BOOKING, new Object[]{bookingId}, new RowMapper<Booking>() {
                @Override
                public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Booking booking= new Booking();
                    booking.setBookingId(rs.getInt("booking_id"));
                    booking.setUserId(rs.getInt("user_id"));
                    booking.setBusRouteId(rs.getInt("bus_route_id"));
                    booking.setSeatNumber(rs.getInt("seat_number"));
                    booking.setDateOfTravel(rs.getDate("date_of_travel"));
                    booking.setStatus(BookingStatus.valueOf(rs.getString("status")));
                    return booking;
                }});
        } catch (Exception e) {
            throw new BookException("Error while fetching booking");
        }

    }

    @Override
    public boolean increaseAvailableSeatCount(Integer busRouteId, Date dateOfTravel) {
        try {
            return jdbcTemplate.update(INCREASE_SEAT_COUNT, busRouteId, dateOfTravel) == 1;
        } catch (Exception e) {
            throw new BookException("Error while increasing seat count");
        }
    }


    private final RowMapper<BusSearchResponse> busSearchResponseRowMapper = ((rs, rowNum) -> {
        return new BusSearchResponse(rs.getString("BUS_NAME")
                , rs.getInt("DISTANCE")
                , rs.getTime("DEPARTURE_TIME")
                , rs.getInt("BUS_ROUTE_ID"));
    });
}
