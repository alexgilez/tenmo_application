package com.techelevator.tenmo.dao;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface AccountDao {

    String getNameById(int userId);

    double getBalance(int userId);

    List<Integer> getTransferToAccounts();

    void sendTransfer(int userFrom, int userTo, double amount);

    void rejectTransferWithSameId(int userFrom, int userTo, double amount);



    }
