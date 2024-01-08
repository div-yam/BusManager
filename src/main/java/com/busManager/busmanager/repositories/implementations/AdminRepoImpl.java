package com.busManager.busmanager.repositories.implementations;

import com.busManager.busmanager.data.request.AddBusRequest;
import com.busManager.busmanager.data.request.UpdateBusRequest;
import com.busManager.busmanager.exceptions.AddBusException;
import com.busManager.busmanager.exceptions.UpdateBusException;
import com.busManager.busmanager.repositories.AdminRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

@Repository
public class AdminRepoImpl implements AdminRepo {

    private static final String ADD_BUS_SQL ="INSERT INTO buses (bus_name, total_seats) VALUES (?, ?);";
    private static final String ADD_ROUTE_SQL="INSERT INTO routes (source, destination, distance, departure_time) VALUES (?, ?, ?, ?);";
    private static final String ADD_BUS_ROUTE_SQL="INSERT INTO bus_routes (bus_id, route_id) VALUES (?, ?);";
    private static final String ADD_SEAT_AVAILABILITY = "SELECT update_seat_availability(?, ?, ?, ?)";
    private static final String ADD_BUS_SCHEDULE_SQL = "INSERT INTO bus_schedule (bus_route_id, day_of_week, active) VALUES (?, ?, TRUE);";
    private static final String DELETE_BUS_SQL =  "UPDATE bus_schedule\n" +
            "SET active = false\n" +
            "FROM bus_routes, buses\n" +
            "WHERE bus_schedule.bus_route_id = bus_routes.bus_route_id\n" +
            "AND bus_routes.bus_id = buses.bus_id\n" +
            "AND buses.bus_name = ?;";
    private static final String UPDATE_BUS_TIME_SQL = "UPDATE routes\n" +
            "SET departure_time = ?\n" +
            "FROM bus_routes\n" +
            "JOIN buses ON bus_routes.bus_id = buses.bus_id\n" +
            "WHERE buses.bus_name = ?\n" +
            "AND bus_routes.route_id = routes.route_id;";
    @Autowired
    JdbcTemplate jdbcTemplate;
    private static Logger LOGGER = LoggerFactory.getLogger(AdminRepoImpl.class);

    @Override
    public Integer addBus(String busName, Integer totalSeats) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(ADD_BUS_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, busName);
                ps.setInt(2, totalSeats);
                return ps;
            }, keyHolder);
            return (Integer) Objects.requireNonNull(keyHolder.getKeys()).get("BUS_ID");
        } catch (Exception e) {
            LOGGER.info(String.valueOf(e));
            LOGGER.info("Bus Name: {}", busName);
            LOGGER.info("Total seats: {}", totalSeats);
            throw new AddBusException("Error while adding bus to the database");
        }
    }

    @Override
    public Integer addRoute(AddBusRequest addBusRequest) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(ADD_ROUTE_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, addBusRequest.getSource());
                ps.setString(2, addBusRequest.getDestination());
                ps.setFloat(3, addBusRequest.getDistance());
                ps.setTime(4, addBusRequest.getDepartureTime());
                return ps;
            }, keyHolder);
            return (Integer) Objects.requireNonNull(keyHolder.getKeys()).get("ROUTE_ID");
        } catch (Exception e) {
            LOGGER.info(String.valueOf(e));
            LOGGER.info("Add Bus Request: {}", addBusRequest);
            throw new AddBusException("Error while adding route to the database");
        }

    }

    @Override
    public Integer addBusRoute(Integer busId, Integer routeId) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(ADD_BUS_ROUTE_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, busId);
                ps.setInt(2, routeId);
                return ps;
            }, keyHolder);
            return (Integer) Objects.requireNonNull(keyHolder.getKeys()).get("BUS_ROUTE_ID");
        } catch (Exception e) {
            LOGGER.info(String.valueOf(e));
            LOGGER.info("BusId: {}", busId);
            LOGGER.info("RouteId {}", routeId);
            throw new AddBusException("Error while adding bus route to the database");
        }
    }

    @Override
    public void addSeatAvailability(Integer weekDay,
                                       Integer busRouteId, Integer totalNumberOfSeats) {
        try {
            jdbcTemplate.execute(ADD_SEAT_AVAILABILITY, new PreparedStatementCallback<Void>() {
                @Override
                public Void doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                    ps.setInt(1, weekDay);
                    ps.setInt(2, busRouteId);
                    ps.setInt(3, totalNumberOfSeats);
                    ps.setInt(4, totalNumberOfSeats);
                    ps.execute();
                    return null;
                }
            });
        } catch (Exception e) {
            LOGGER.error("Error while adding seat availability to the database. WeekDay: {}, BusRouteId: {}, TotalNumberOfSeats: {}", weekDay, busRouteId, totalNumberOfSeats);
            LOGGER.error("Exception details: ", e);
            throw new AddBusException("Error while adding seat availability to the database");
        }
    }

    @Override
    public Integer addBusSchedule(Integer busRoutedId, String weekDay) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(ADD_BUS_SCHEDULE_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, busRoutedId);
                ps.setString(2, weekDay);
                return ps;
            }, keyHolder);
            return (Integer) Objects.requireNonNull(keyHolder.getKeys()).get("SCHEDULE_ID");
        } catch (Exception e) {
            LOGGER.error("Error while adding bus schedule to the database. BusRouteId: {}, WeekDay: {}", busRoutedId, weekDay);
            LOGGER.error("Exception details: ", e);
            throw new AddBusException("Error while adding bus schedule to the database");
        }
    }

    @Override
    public boolean deleteBus(String busId) {
        try {
            return jdbcTemplate.update(DELETE_BUS_SQL, busId) > 0;
        } catch (Exception e) {
            LOGGER.error("Error while deleting bus with ID {}: {}", busId, e.getMessage());
            throw new UpdateBusException("Error while deleting bus");
        }

    }
    @Override
    public boolean updateBus(UpdateBusRequest updateBusRequest) {
        try {
            return jdbcTemplate.update(UPDATE_BUS_TIME_SQL,
                    updateBusRequest.getNewDepartureTime(), updateBusRequest.getBusName()) > 0;
        } catch (Exception e) {
            LOGGER.error("Error while updating bus information for bus with name {}: {}",
                    updateBusRequest.getBusName(), e.getMessage());
            throw new UpdateBusException("Error while updating bus information");
        }

    }
}
