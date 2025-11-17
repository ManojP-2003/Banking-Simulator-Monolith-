package com.bank.banking_monolith.service;

import com.bank.banking_monolith.exception.AccountNotFoundException;
import com.bank.banking_monolith.exception.InvalidAmountException;
import com.bank.banking_monolith.model.Account;
import com.bank.banking_monolith.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    public AccountRepository accountRepository;

    // Generate account number
    private String generateAccountNumber(String holderName) {
        String initials = holderName.replaceAll("[^A-Za-z]", "");
        initials = initials.length() >= 3 ? initials.substring(0,3).toUpperCase() : initials.toUpperCase();
        int rand = (int)(Math.random() * 9000) + 1000;
        return initials + rand;
    }

    @Override
    public Account createAccount(Account accountRequest) {

        logger.info("Creating account for holderName={}", accountRequest.getHolderName());

        if (accountRequest.getHolderName() == null || accountRequest.getHolderName().isBlank()) {
            logger.warn("Empty holder name detected");
            throw new InvalidAmountException("Holder name cannot be empty");
        }

        String accountNumber = generateAccountNumber(accountRequest.getHolderName());
        logger.debug("Generated accountNumber={}", accountNumber);

        Account acc = new Account();
        acc.setHolderName(accountRequest.getHolderName());
        acc.setAccountNumber(accountNumber);
        acc.setBalance(0.0);
        acc.setStatus("ACTIVE");
        acc.setCreatedAt(new Date());

        return accountRepository.save(acc);
    }

    @Override
    public Account getAccountByNumber(String accountNumber) {
        logger.info("Fetching account by accountNumber={}", accountNumber);

        Account acc = accountRepository.findByAccountNumber(accountNumber);
        if (acc == null) {
            logger.error("Account not found {}", accountNumber);
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }

        return acc;
    }

    @Override
    public List<Account> getAllAccounts() {
        logger.info("Fetching all accounts");
        return accountRepository.findAll();
    }

    @Override
    public void deleteAccount(String accountNumber) {
        logger.warn("Deleting account {}", accountNumber);

        Account acc = accountRepository.findByAccountNumber(accountNumber);
        if (acc == null) {
            logger.error("Cannot delete. Account not found {}", accountNumber);
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }

        accountRepository.delete(acc);
    }
}
