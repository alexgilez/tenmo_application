package com.techelevator.tenmo.dao;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface AccountDao {

    double getBalance(int userId);

    List<Integer> getTransferToAccounts();

    String sendTransfer(int userFrom, int userTo, double amount);



    }
