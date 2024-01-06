drop database busdb;
drop user busmanager;
create user busmanager with password 'password';
create database busdb with template=template0 owner=busmanager;
\connect busdb;
alter default privileges grant all on tables to busmanager;

-- user is a reserved keyword in postgres
-- Users Table
CREATE TABLE Users (
    user_id SERIAL PRIMARY KEY,
    name VARCHAR(80) not null,
    email VARCHAR(80) UNIQUE,
    password VARCHAR(30) not null,
    role VARCHAR(10) not null
);

-- Buses Table
CREATE TABLE Buses (
    bus_id SERIAL PRIMARY KEY,
    bus_name VARCHAR(30) not null,
    total_seats INT not null
);

-- Routes Table
CREATE TABLE Routes (
    route_id SERIAL PRIMARY KEY,
    source VARCHAR(20) not null,
    destination VARCHAR(20) not null,
    distance FLOAT not null,
    eta TIME not null
);

-- Bus_Routes Table
CREATE TABLE Bus_Routes (
    bus_route_id SERIAL PRIMARY KEY,
    bus_id INT REFERENCES Buses(bus_id),
    route_id INT REFERENCES Routes(route_id)
);

-- Bus_Schedule Table
CREATE TABLE Bus_Schedule (
    schedule_id SERIAL PRIMARY KEY,
    bus_route_id INT REFERENCES Bus_Routes(bus_route_id),
    day_of_week VARCHAR(10) not null,
    active BOOLEAN not null
);

-- Seat_Availability Table
CREATE TABLE Seat_Availability (
    availability_id SERIAL PRIMARY KEY,
    bus_route_id INT REFERENCES Bus_Routes(bus_route_id),
    date DATE not null,
    seats_available INT not null,
    total_seats INT
);

-- Bookings Table
CREATE TABLE Bookings (
    booking_id SERIAL PRIMARY KEY,
    user_id INT REFERENCES Users(user_id),
    bus_route_id INT REFERENCES Bus_Routes(bus_route_id),
    date_of_travel DATE not null,
    seat_number INT not null,
    status VARCHAR(10)
);