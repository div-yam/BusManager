
--Add bus
INSERT INTO buses (bus_name, total_seats) VALUES ('Express Line 1', 50);
INSERT INTO buses (bus_name, total_seats) VALUES ('Express Line 2', 50);

INSERT INTO routes (source, destination, distance, departure_time) VALUES ('CityA', 'CityB', 120, '08:00:00');
INSERT INTO routes (source, destination, distance, departure_time) VALUES ('CityB', 'CityC', 100, '10:00:00');

INSERT INTO routes (source, destination, distance, departure_time) VALUES ('CityA', 'CityB', 120, '12:00:00');
INSERT INTO routes (source, destination, distance, departure_time) VALUES ('CityB', 'CityC', 100, '14:00:00');

INSERT INTO bus_routes (bus_id, route_id) VALUES (1, 1);

INSERT INTO bus_routes (bus_id, route_id) VALUES (1, 2);

INSERT INTO bus_routes (bus_id, route_id) VALUES (2, 3);

INSERT INTO bus_routes (bus_id, route_id) VALUES (2, 4);



DO $$
DECLARE
    current_date DATE := CURRENT_DATE;
    is_operational BOOLEAN;
BEGIN
    FOR i IN 0..29 LOOP
        is_operational := EXTRACT(DOW FROM current_date + i) IN (1, 3); -- 1 for Monday, 3 for Wednesday

        IF is_operational THEN
            INSERT INTO seat_availability (bus_route_id, date, seats_available, total_seats)
            VALUES (3, current_date + i, 50, 50);
        END IF;
    END LOOP;
END $$;

DO $$
DECLARE
    current_date DATE := CURRENT_DATE;
    is_operational BOOLEAN;
BEGIN
    FOR i IN 0..29 LOOP
        is_operational := EXTRACT(DOW FROM current_date + i) IN (1, 3); -- 1 for Monday, 3 for Wednesday

        IF is_operational THEN
            INSERT INTO seat_availability (bus_route_id, date, seats_available, total_seats)
            VALUES (4, current_date + i, 50, 50);
        END IF;
    END LOOP;
END $$;

INSERT INTO bus_schedule (bus_route_id, day_of_week, active) VALUES (1, 'Monday', TRUE);
INSERT INTO bus_schedule (bus_route_id, day_of_week, active) VALUES (1, 'Wednesday', TRUE);
INSERT INTO bus_schedule (bus_route_id, day_of_week, active) VALUES (2, 'Monday', TRUE);
INSERT INTO bus_schedule (bus_route_id, day_of_week, active) VALUES (2, 'Wednesday', TRUE);
INSERT INTO bus_schedule (bus_route_id, day_of_week, active) VALUES (3, 'Monday', TRUE);
INSERT INTO bus_schedule (bus_route_id, day_of_week, active) VALUES (3, 'Wednesday', TRUE);
INSERT INTO bus_schedule (bus_route_id, day_of_week, active) VALUES (4, 'Monday', TRUE);
INSERT INTO bus_schedule (bus_route_id, day_of_week, active) VALUES (4, 'Wednesday', TRUE);
--Add bus

--delete
UPDATE bus_schedule
SET active = true
FROM bus_routes, buses
WHERE bus_schedule.bus_route_id = bus_routes.bus_route_id
AND bus_routes.bus_id = buses.bus_id
AND buses.bus_name = 'Express Line 1';

---search buses
SELECT
    buses.bus_name,
    routes.departure_time,
    SUM(routes.distance) AS total_distance
FROM
    bus_schedule
JOIN
    bus_routes ON bus_schedule.bus_route_id = bus_routes.bus_route_id
JOIN
    buses ON bus_routes.bus_id = buses.bus_id
JOIN
    routes ON bus_routes.route_id = routes.route_id
WHERE
    routes.source = 'CityA'
    AND routes.destination = 'CityB'
    AND bus_schedule.active = TRUE
    AND bus_schedule.day_of_week = 'Monday'
GROUP BY
    buses.bus_name,
    routes.departure_time;


-----
SELECT
    buses.bus_name,
    routes.distance,
    routes.departure_time,
    bus_routes.bus_route_id
FROM
    buses
JOIN
    bus_routes ON buses.bus_id = bus_routes.bus_id
JOIN
    routes ON bus_routes.route_id = routes.route_id
JOIN
    bus_schedule ON bus_routes.bus_route_id = bus_schedule.bus_route_id
WHERE
    routes.source = 'CityB'
    AND routes.destination = 'CityC'
    AND bus_schedule.active = TRUE
   AND bus_schedule.day_of_week = 'Monday';


---check eligibility
SELECT
    sa.total_seats - COUNT(b.booking_id) AS seats_available,
    sa.total_seats
FROM
    seat_availability sa
LEFT JOIN
    bookings b ON sa.bus_route_id = b.bus_route_id AND sa.date = b.date_of_travel
WHERE
    sa.bus_route_id = 1
    AND sa.date = '2024-01-15'
GROUP BY
    sa.total_seats, sa.bus_route_id;

    --
--user
Insert into users(name,email,password,role) values('Divyam','divyam@ga','divyam','ADMIN');

--booking
INSERT INTO bookings (user_id, bus_route_id, date_of_travel, seat_number, status)
VALUES (1, 1, '2024-01-10', 1, 'HOLD');

UPDATE seat_availability
SET seats_available = seats_available - 1
WHERE bus_route_id = 1 AND date = '2024-01-10';

SELECT seat_number
FROM bookings
WHERE bus_route_id = 1
AND date_of_travel = '2024-01-10'
AND (status = 'BOOK' OR (status = 'HOLD' AND time_of_booking >= NOW() - INTERVAL '5 minutes' ));


UPDATE bookings
SET status = 'BOOK'
WHERE booking_id = 1
AND user_id = 1
AND status = 'HOLD'
AND time_of_booking >= NOW() - INTERVAL '5 minutes';

UPDATE bookings
SET status = 'CANCEL'
WHERE booking_id = 1
AND status = 'BOOK';


-----

