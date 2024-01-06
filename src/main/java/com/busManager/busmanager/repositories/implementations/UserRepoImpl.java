package com.busManager.busmanager.repositories.implementations;

import com.busManager.busmanager.data.dto.BusSearchResponse;
import com.busManager.busmanager.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    private final RowMapper<BusSearchResponse> busSearchResponseRowMapper = ((rs, rowNum) -> {
        return new BusSearchResponse(rs.getString("BUS_NAME")
                , rs.getInt("DISTANCE")
                , rs.getTime("DEPARTURE_TIME")
                , rs.getInt("BUS_ROUTE_ID"));
    });
}
