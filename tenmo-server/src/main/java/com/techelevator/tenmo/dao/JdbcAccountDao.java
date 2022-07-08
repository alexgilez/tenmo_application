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
    public String getNameById(int accountId) {
        String receivedName = "";

        String sql = "SELECT username FROM tenmo_user " +
                        "JOIN tenmo_account ON tenmo_user.user_id = tenmo_account.user_id " +
                        "WHERE tenmo_account.account_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
            if (results.next()) {
                receivedName = results.getString("username");
            }
        } catch (UserNotFoundException ex) {
            System.out.println("User not found.");
        } return receivedName;
    }

    @Override
    public double getBalance(int accountId) {
        double balance = 0;

        String sql = "SELECT balance FROM tenmo_account WHERE account_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
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

        String sql = "SELECT account_id FROM tenmo_account;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
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
    public int rejectTransferWithSameId(int accountFrom, int accountTo, double amount){

            String sql = "INSERT INTO tenmo_transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                    "VALUES (2, 3, ?, ?, ?);";

            Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, accountFrom, accountTo, amount);

            return newId;

        }

    @Override
    public int sendTransfer(int accountFrom, int accountTo, double amount){

        String sql = "INSERT INTO tenmo_transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                        "VALUES (2, 2, ?, ?, ?);";

        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, accountFrom, accountTo, amount);

        if (amount <= accountDao.getBalance(accountFrom)) {

        sql = "UPDATE tenmo_account" +
                        "SET balance = balance - ?" +
                        "WHERE account_id = ?;";

        jdbcTemplate.update(sql, amount, accountFrom);

        sql = "UPDATE tenmo_account" +
                "SET balance = balance + ?" +
                "WHERE account_id = ?;";

        jdbcTemplate.update(sql, amount, accountTo);
    }
        return newId;
    }
}
