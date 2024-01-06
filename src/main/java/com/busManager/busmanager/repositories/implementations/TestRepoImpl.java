package com.busManager.busmanager.repositories.implementations;

import com.busManager.busmanager.repositories.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;


@Repository
public class TestRepoImpl implements TestRepo {

    private static  String SQL_CREATE="INSERT INTO BUS(ID) VALUES (?)";

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public String create(String id) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection ->{
            PreparedStatement ps= connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,id);
            return ps;
        },keyHolder);
        return String.valueOf( keyHolder.getKeys().get("ID"));
    }
}
