package com.techelevator.tenmo.dao;

import java.util.List;

public interface AccountDao {
    double getBalance(int userId);

    List<Integer> getTransferToAccounts(int userId);

    String sendTransfer(int userFrom, int userTo, double amount);



    }
