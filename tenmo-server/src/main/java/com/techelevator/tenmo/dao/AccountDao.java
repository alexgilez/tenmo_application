package com.techelevator.tenmo.dao;

import java.util.List;

public interface AccountDao {
    double getBalance(int userId, int accountId);

    List<Integer> getTransferToAccounts(int userId);
}
