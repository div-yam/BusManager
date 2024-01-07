package com.busManager.busmanager.repositories.implementations;

import com.busManager.busmanager.data.dto.BusSearchResponse;
import com.busManager.busmanager.data.response.CheckEligibilityResponse;
import com.busManager.busmanager.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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

    private static final String GET_ELIGIBILITY_RESPONSE = "SELECT\n" +
            "    sa.total_seats - COUNT(b.booking_id) AS seats_available,\n" +
            "    sa.total_seats\n" +
            "FROM\n" +
            "    seat_availability sa\n" +
            "LEFT JOIN\n" +
            "    bookings b ON sa.bus_route_id = b.bus_route_id AND sa.date = b.date_of_travel\n" +
            "WHERE\n" +
            "    sa.bus_route_id = ?\n" +
            "    AND sa.date = ?\n" +
            "GROUP BY\n" +
            "    sa.total_seats, sa.bus_route_id;";

    private static final String HOLD_BOOKING="INSERT INTO bookings (user_id, bus_route_id, date_of_travel, seat_number, status)\n" +
            "VALUES (?, ?, ?, ?, 'HOLD');";

    private static final String UPDATE_SEAT_COUNT="UPDATE seat_availability\n" +
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
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<BusSearchResponse> getBuses(String source, String destination, String day) {
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

    }

    @Override
    public CheckEligibilityResponse getEligibilityResponse(Integer busRouteId, String date) {
        return jdbcTemplate.queryForObject(GET_ELIGIBILITY_RESPONSE, new Object[]{busRouteId, date}, new RowMapper<CheckEligibilityResponse>() {
            @Override
            public CheckEligibilityResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                CheckEligibilityResponse checkEligibilityResponse = new CheckEligibilityResponse();
                checkEligibilityResponse.setNumberOfSeats(rs.getInt("seats_available"));
                checkEligibilityResponse.setTotalNumberOfSeats(rs.getInt("total_seats"));
                return checkEligibilityResponse;
            }
        });
    }

    @Override
    public Integer holdBooking(Integer userId, Integer busRouteId, Date dateOfTravel, int seatNumber) {
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
    }

    @Override
    public boolean updateAvailableSeatCount(Integer busRouteId, Date dateOfTravel) {
        return jdbcTemplate.update(UPDATE_SEAT_COUNT, busRouteId, dateOfTravel) == 1;
    }

    @Override
    public List getSeatsBookedOrHold(Date dateOfTravel, Integer bookingId, Integer busRouteID) {
        return jdbcTemplate.queryForObject(GET_BOOKED_SEATS, List.class, busRouteID, dateOfTravel);
    }

    @Override
    public boolean updateBookingStatus(Integer bookingId, Integer userId) {
        return jdbcTemplate.update(UPDATE_BOOKING, bookingId, userId) == 1;
    }


    private final RowMapper<BusSearchResponse> busSearchResponseRowMapper = ((rs, rowNum) -> {
        return new BusSearchResponse(rs.getString("BUS_NAME")
                , rs.getInt("DISTANCE")
                , rs.getTime("DEPARTURE_TIME")
                , rs.getInt("BUS_ROUTE_ID"));
    });
}
