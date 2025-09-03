package com.bezina.water_delivery.order_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderNoGenerator {// Sequence imitation
    private  JdbcTemplate jdbcTemplate;

    public OrderNoGenerator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Transactional
    public synchronized Long nextOrderNo() {

        jdbcTemplate.update("INSERT INTO orders_db.order_sequence () VALUES ();");
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    }
}
