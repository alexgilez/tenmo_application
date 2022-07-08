package com.techelevator.tenmo.dao;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface AccountDao {

    String getNameById(int accountId);

    double getBalance(int accountId);

    List<Integer> getTransferToAccounts();

    int sendTransfer(int accountFrom, int accountTo, double amount);

    int rejectTransferWithSameId(int accountFrom, int accountTo, double amount);



    }
