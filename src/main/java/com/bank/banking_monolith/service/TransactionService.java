package com.bank.banking_monolith.service;

import com.bank.banking_monolith.model.Transaction;
import java.util.List;

public interface TransactionService {

    Transaction deposit(String accountNumber, Double amount);

    Transaction withdraw(String accountNumber, Double amount);

    Transaction transfer(String sourceAccount, String destinationAccount, Double amount);

    List<Transaction> getTransactions(String accountNumber);
}
