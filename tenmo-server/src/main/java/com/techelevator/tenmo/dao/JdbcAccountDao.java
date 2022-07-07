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
    public String getNameById(int user_id) {
        String receivedName = "";

        String sql = "SELECT username FROM tenmo_user WHERE user_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user_id);
            if (results.next()) {
                receivedName = results.getString("username");
            }
        } catch (UserNotFoundException ex) {
            System.out.println("User not found.");
        } return receivedName;
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
    public List<Integer> getTransferToAccounts() {
        List<Integer> availableAccounts = new ArrayList<>();

        String sql = "SELECT user_id FROM tenmo_user;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                availableAccounts.add(results.getInt("user_id"));
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
    public void rejectTransferWithSameId(int userFrom, int userTo, double amount){

            String sql = "INSERT INTO tenmo_transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                    "VALUES (2, 3, ?, ?, ?);";

            Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, userFrom, userTo, amount);

        }

    @Override
    public void sendTransfer(int userFrom, int userTo, double amount){

        String sql = "INSERT INTO tenmo_transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                        "VALUES (2, 2, ?, ?, ?);";

        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, userFrom, userTo, amount);

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
    }
}
