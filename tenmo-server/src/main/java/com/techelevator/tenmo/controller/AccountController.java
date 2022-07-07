package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountDao accountDao;
    private UserDao userDao;

    public AccountController(AccountDao accountDao, UserDao userDao){
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public double getBalance(@Valid @PathVariable int userId, Principal user){
        String username = user.getName();
        this.userDao.findIdByUsername(username);
        double balance = accountDao.getBalance(userId);
        return balance;
    }

    @RequestMapping(path = "/account", method = RequestMethod.GET)
    public List<Integer> listAvailableAccounts(Principal user) {
        String username = user.getName();
        this.userDao.findIdByUsername(username);
        List<Integer> available = accountDao.getTransferToAccounts();


        return available;
    }

    /*
    @RequestMapping(path = "{userId}", method = RequestMethod.PUT)
    public double executeTransfer(@Valid @PathVariable int userId, Principal user) {
        String username = user.getName();
        this.userDao.findIdByUsername(username);
        double balance = accountDao.getBalance(userId);

    }
     */


    //Number 4
    // step one -- reveal list of account_id's to send
    //amount to
    //step two -- get transfer amount from a
    // balance from account_id
    //step three -- check that transfer amount is not more
    //than balance, also not 0 or negative
    //step four -- decrease "from" account/increase "to account"



}
