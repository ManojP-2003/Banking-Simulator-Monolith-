package com.bank.banking_monolith.service;

import com.bank.banking_monolith.model.Account;
import java.util.List;

public interface AccountService {

    Account createAccount(Account account);

    Account getAccountByNumber(String accountNumber);

    List<Account> getAllAccounts();

    void deleteAccount(String accountNumber);
}
