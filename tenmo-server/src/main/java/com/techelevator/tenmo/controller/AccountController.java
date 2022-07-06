package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
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

    @RequestMapping(path = "{userId}/{id}/balance", method = RequestMethod.GET)
    public double getBalance(@Valid @PathVariable int userId, @PathVariable int id, Principal user){
        String username = user.getName();
        this.userDao.findIdByUsername(username);
        double balance = accountDao.getBalance(userId, id);
        return balance;
    }

    @RequestMapping(path = "{userId}", method = RequestMethod.GET)
    public List<Integer> listAvailableAccounts(@Valid @PathVariable int userId) {

    }
    //Number 4
    // step one -- reveal list of account_id's to send
    //amount to
    //step two -- get transfer amount from a
    // balance from account_id
    //step three -- check that transfer amount is not more
    //than balance, also not 0 or negative
    //step four -- decrease "from" account/increase "to account"



}
