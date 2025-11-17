package com.bank.banking_monolith.controller;

import com.bank.banking_monolith.model.Transaction;
import com.bank.banking_monolith.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @PutMapping("/{accountNumber}/deposit")
    public ResponseEntity<Transaction> deposit(
            @PathVariable String accountNumber,
            @RequestParam Double amount) {

        logger.info("API: Deposit requested for account={} amount={}", accountNumber, amount);
        Transaction txn = transactionService.deposit(accountNumber, amount);
        logger.info("Deposit successful txnId={}", txn.getTransactionId());
        return ResponseEntity.ok(txn);
    }

    @PutMapping("/{accountNumber}/withdraw")
    public ResponseEntity<Transaction> withdraw(
            @PathVariable String accountNumber,
            @RequestParam Double amount) {

        logger.info("API: Withdraw requested for account={} amount={}", accountNumber, amount);
        Transaction txn = transactionService.withdraw(accountNumber, amount);
        logger.info("Withdraw successful txnId={}", txn.getTransactionId());
        return ResponseEntity.ok(txn);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(
            @RequestParam String sourceAccount,
            @RequestParam String destinationAccount,
            @RequestParam Double amount) {

        logger.info("API: Transfer requested {} -> {} amount={}", sourceAccount, destinationAccount, amount);
        Transaction txn = transactionService.transfer(sourceAccount, destinationAccount, amount);
        logger.info("Transfer successful txnId={}", txn.getTransactionId());
        return ResponseEntity.ok(txn);
    }

    @GetMapping("/{accountNumber}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String accountNumber) {
        logger.info("API: Get transactions for account={}", accountNumber);
        List<Transaction> txns = transactionService.getTransactions(accountNumber);
        return ResponseEntity.ok(txns);
    }
}
