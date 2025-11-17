package com.bank.banking_monolith.controller;

import com.bank.banking_monolith.model.Account;
import com.bank.banking_monolith.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    // Create Account
    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        logger.info("API: Create account request holderName={}", account.getHolderName());
        return accountService.createAccount(account);
    }

    // Get account by number
    @GetMapping("/{accountNumber}")
    public Account getAccount(@PathVariable String accountNumber) {
        logger.info("API: Get account {}", accountNumber);
        return accountService.getAccountByNumber(accountNumber);
    }

    // Get all accounts
    @GetMapping
    public List<Account> getAllAccounts() {
        logger.info("API: List all accounts");
        return accountService.getAllAccounts();
    }

    // Delete account
    @DeleteMapping("/{accountNumber}")
    public String deleteAccount(@PathVariable String accountNumber) {
        logger.info("API: Delete account {}", accountNumber);
        accountService.deleteAccount(accountNumber);
        return "Account deleted successfully: " + accountNumber;
    }
}
