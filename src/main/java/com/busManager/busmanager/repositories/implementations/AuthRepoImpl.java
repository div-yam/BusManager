package com.busManager.busmanager.repositories.implementations;

import com.busManager.busmanager.data.UserRole;
import com.busManager.busmanager.data.dto.AddUserRequest;
import com.busManager.busmanager.data.dto.User;
import com.busManager.busmanager.data.request.SignInRequest;
import com.busManager.busmanager.repositories.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

@Repository
public class AuthRepoImpl implements AuthRepo {

    private static final String INSERT_USER="INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, 'USER');";
    private static final String GET_USER= "SELECT * FROM users WHERE email = ?;";
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public User addUser(AddUserRequest addUserRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, addUserRequest.getName());
            ps.setString(2, addUserRequest.getEmail());
            ps.setString(3,addUserRequest.getPassword());
            return ps;
        }, keyHolder);
        User user = new User();
        user.setUserId((Integer) Objects.requireNonNull(keyHolder.getKeys()).get("USER_ID"));
        user.setName((String) Objects.requireNonNull(keyHolder.getKeys()).get("name"));
        user.setRole(UserRole.valueOf(String.valueOf(Objects.requireNonNull(keyHolder.getKeys()).get("role"))));
        return user;
    }

    @Override
    public User getUser(String email) {
        return jdbcTemplate.queryForObject(GET_USER, new Object[]{email}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(UserRole.valueOf(rs.getString("role")));
                return user;
            }
        });
    }
}
