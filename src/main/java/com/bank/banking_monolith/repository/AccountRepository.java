package com.bank.banking_monolith.repository;

import com.bank.banking_monolith.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

    Account findByAccountNumber(String accountNumber);

}
