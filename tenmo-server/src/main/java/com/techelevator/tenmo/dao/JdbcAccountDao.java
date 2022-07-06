package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.UserNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public double getBalance(int userId, int accountId) {
        double balance = 0;

        String sql = "SELECT balance FROM tenmo_account WHERE user_id = ? AND account_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, accountId);
            if (results.next()) {
                balance = results.getDouble("balance");
            }
        } catch (UserNotFoundException ex) {
            System.out.println("User not found.");
        } return balance;
    }

    public List<Integer> getTransferToAccounts(int userId) {
        List<Integer> availableAccounts = new ArrayList<>();

        String sql = "SELECT account_id FROM tenmo_account WHERE user_id <> ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            availableAccounts.add(results.getInt("account_id"));
        }
        return availableAccounts;

    }



}
