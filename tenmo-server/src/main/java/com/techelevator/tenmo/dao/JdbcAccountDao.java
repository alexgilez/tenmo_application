package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.UserNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;



@Component
public class JdbcAccountDao implements AccountDao {
    private final JdbcTemplate jdbcTemplate;
    private AccountDao accountDao;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public double getBalance(int userId) {
        double balance = 0;

        String sql = "SELECT balance FROM tenmo_account WHERE user_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                balance = results.getDouble("balance");
            }
        } catch (UserNotFoundException ex) {
            System.out.println("User not found.");
        } return balance;
    }
    @Override
    public List<Integer> getTransferToAccounts(int userId) {
        List<Integer> availableAccounts = new ArrayList<>();

        String sql = "SELECT account_id FROM tenmo_account WHERE user_id <> ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                availableAccounts.add(results.getInt("account_id"));
            }
        } catch (UserNotFoundException ex){
            System.out.println("User not found.");
        }
        return availableAccounts;
    }
/*
    @Override
    public double executeTransfer(double transferAmount, int accountIdA, int accountIdB) {

        String sql = "BEGIN TRANSACTION;" +
                "UPDATE tenmo_account" +
                "SET balance = balance - ?" +
                "WHERE account_id = ?;" +
                "UPDATE tenmo_account" +
                "SET balance = balance + ?" +
                "WHERE account_id = ?;" +
                "COMMIT;";
        try {
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferAmount, accountIdA, transferAmount, accountIdB);

        } catch (UserNotFoundException ex){
            System.out.println("User not found.");
        }
    }

 */





    @Override
    public String sendTransfer(int userFrom, int userTo, double amount){
        if (userFrom == userTo){

            String sql = "INSERT INTO tenmo_transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                    "VALUES (2, 3, ?, ?, ?);";

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userFrom, userTo, amount);

            return "Cannot send money to your own account";
        }

        String sql = "INSERT INTO tenmo_transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                        "VALUES (2, 2, ?, ?, ?);";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userFrom, userTo, amount);

        if (amount <= accountDao.getBalance(userFrom)) {

        sql = "UPDATE tenmo_account" +
                        "SET balance = balance - ?" +
                        "WHERE account_id = ?;";

        jdbcTemplate.update(sql, amount, userFrom);

        sql = "UPDATE tenmo_account" +
                "SET balance = balance + ?" +
                "WHERE account_id = ?;";

        jdbcTemplate.update(sql, amount, userTo);
    }
        return "Transfer approved.";
    }
}
