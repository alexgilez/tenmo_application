package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.UserNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcAccountDao implements AccountDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public double getBalance(int accountId) {
        String sql = "SELECT balance FROM account WHERE account_id = ?";
        SqlRowSet results = null;
        double balance = 0;

        try {
            results = jdbcTemplate.queryForRowSet(sql, accountId);
            if (results.next()) {
                balance = results.getDouble("balance");
            }
        } catch (UserNotFoundException ex) {
            System.out.println("User not found.");
        } return balance;
    }

}
