package com.bank.banking_monolith.service;

import com.bank.banking_monolith.exception.AccountNotFoundException;
import com.bank.banking_monolith.exception.InvalidAmountException;
import com.bank.banking_monolith.exception.InsufficientBalanceException;
import com.bank.banking_monolith.model.Account;
import com.bank.banking_monolith.model.Transaction;
import com.bank.banking_monolith.repository.AccountRepository;
import com.bank.banking_monolith.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    public AccountRepository accountRepository;

    @Autowired
    public TransactionRepository transactionRepository;

    private String generateTransactionId() {
        return "TXN-" + new Random().nextInt(100000);
    }

    @Override
    public Transaction deposit(String accountNumber, Double amount) {

        logger.info("Deposit called - account={} amount={}", accountNumber, amount);

        if (amount <= 0) {
            logger.warn("Invalid deposit amount: {}", amount);
            throw new InvalidAmountException("Deposit amount must be greater than 0");
        }

        Account acc = accountRepository.findByAccountNumber(accountNumber);

        if (acc == null) {
            logger.error("Deposit failed. Account not found {}", accountNumber);
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }

        acc.setBalance(acc.getBalance() + amount);
        accountRepository.save(acc);

        Transaction txn = new Transaction();
        txn.setTransactionId(generateTransactionId());
        txn.setType("DEPOSIT");
        txn.setAmount(amount);
        txn.setTimestamp(new Date());
        txn.setStatus("SUCCESS");
        txn.setSourceAccount(accountNumber);
        txn.setDestinationAccount(null);

        logger.debug("Saved transaction id={}", txn.getTransactionId());
        return transactionRepository.save(txn);
    }

    @Override
    public Transaction withdraw(String accountNumber, Double amount) {

        logger.info("Withdraw called - account={} amount={}", accountNumber, amount);

        if (amount <= 0) {
            logger.warn("Invalid withdraw amount: {}", amount);
            throw new InvalidAmountException("Withdrawal amount must be greater than 0");
        }

        Account acc = accountRepository.findByAccountNumber(accountNumber);

        if (acc == null) {
            logger.error("Withdraw failed. Account not found {}", accountNumber);
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }

        if (acc.getBalance() < amount) {
            logger.warn("Insufficient balance for withdrawal for account {}", accountNumber);
            throw new InsufficientBalanceException("Insufficient balance!");
        }

        acc.setBalance(acc.getBalance() - amount);
        accountRepository.save(acc);

        Transaction txn = new Transaction();
        txn.setTransactionId(generateTransactionId());
        txn.setType("WITHDRAW");
        txn.setAmount(amount);
        txn.setTimestamp(new Date());
        txn.setStatus("SUCCESS");
        txn.setSourceAccount(accountNumber);
        txn.setDestinationAccount(null);

        logger.debug("Withdraw transaction saved id={}", txn.getTransactionId());
        return transactionRepository.save(txn);
    }

    @Override
    public Transaction transfer(String sourceAccount, String destinationAccount, Double amount) {

        logger.info("Transfer called {} -> {} amount={}", sourceAccount, destinationAccount, amount);

        if (amount <= 0) {
            logger.warn("Invalid transfer amount: {}", amount);
            throw new InvalidAmountException("Transfer amount must be greater than 0");
        }

        Account src = accountRepository.findByAccountNumber(sourceAccount);
        Account dest = accountRepository.findByAccountNumber(destinationAccount);

        if (src == null) {
            logger.error("Source account not found {}", sourceAccount);
            throw new AccountNotFoundException("Source account not found: " + sourceAccount);
        }

        if (dest == null) {
            logger.error("Destination account not found {}", destinationAccount);
            throw new AccountNotFoundException("Destination account not found: " + destinationAccount);
        }

        if (src.getBalance() < amount) {
            logger.warn("Insufficient balance for transfer from {}", sourceAccount);
            throw new InsufficientBalanceException("Insufficient balance for transfer!");
        }

        src.setBalance(src.getBalance() - amount);
        dest.setBalance(dest.getBalance() + amount);

        accountRepository.save(src);
        accountRepository.save(dest);

        Transaction txn = new Transaction();
        txn.setTransactionId(generateTransactionId());
        txn.setType("TRANSFER");
        txn.setAmount(amount);
        txn.setTimestamp(new Date());
        txn.setStatus("SUCCESS");
        txn.setSourceAccount(sourceAccount);
        txn.setDestinationAccount(destinationAccount);

        logger.debug("Transfer transaction saved id={}", txn.getTransactionId());
        return transactionRepository.save(txn);
    }

    @Override
    public List<Transaction> getTransactions(String accountNumber) {

        logger.info("Fetching transactions for account={}", accountNumber);

        List<Transaction> sent = transactionRepository.findBySourceAccount(accountNumber);
        List<Transaction> received = transactionRepository.findByDestinationAccount(accountNumber);

        List<Transaction> all = new ArrayList<>();
        all.addAll(sent);
        all.addAll(received);

        return all;
    }
}
