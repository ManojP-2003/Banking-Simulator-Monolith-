package com.bank.banking_monolith.repository;

import com.bank.banking_monolith.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

    List<Transaction> findBySourceAccount(String accountNumber);

    List<Transaction> findByDestinationAccount(String accountNumber);
}
