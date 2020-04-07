package com.exodus.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert() {
        String sql = "INSERT INTO user(name, age) values(?, ?);";
        String name = UUID.randomUUID().toString().substring(0, 8);

        jdbcTemplate.update(sql, name, 30);
    }
}
