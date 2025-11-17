package com.bank.banking_monolith.service;

import com.bank.banking_monolith.exception.AccountNotFoundException;
import com.bank.banking_monolith.exception.InvalidAmountException;
import com.bank.banking_monolith.exception.InsufficientBalanceException;
import com.bank.banking_monolith.model.Account;
import com.bank.banking_monolith.model.Transaction;
import com.bank.banking_monolith.repository.AccountRepository;
import com.bank.banking_monolith.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class TransactionServiceImplTest {

    private TransactionServiceImpl service;
    private AccountRepository accRepo;
    private TransactionRepository txnRepo;

    @BeforeEach
    void setup() {
        accRepo = Mockito.mock(AccountRepository.class);
        txnRepo = Mockito.mock(TransactionRepository.class);

        service = new TransactionServiceImpl();
        service.accountRepository = accRepo;
        service.transactionRepository = txnRepo;
    }

    @Test
    void testDeposit() {
        Account acc = new Account();
        acc.setAccountNumber("A1");
        acc.setBalance(100.0);

        Mockito.when(accRepo.findByAccountNumber("A1")).thenReturn(acc);
        Mockito.when(txnRepo.save(any(Transaction.class))).thenAnswer(i -> i.getArgument(0));

        Transaction txn = service.deposit("A1", 50.0);

        assertEquals(150.0, acc.getBalance());
        assertEquals("DEPOSIT", txn.getType());
    }

    @Test
    void testDepositInvalidAmount() {
        assertThrows(InvalidAmountException.class,
                () -> service.deposit("A1", -10.0));
    }

    @Test
    void testDepositAccountNotFound() {
        Mockito.when(accRepo.findByAccountNumber("X")).thenReturn(null);

        assertThrows(AccountNotFoundException.class,
                () -> service.deposit("X", 50.0));
    }

    @Test
    void testWithdrawSuccess() {
        Account acc = new Account();
        acc.setBalance(200.0);
        Mockito.when(accRepo.findByAccountNumber("A")).thenReturn(acc);
        Mockito.when(txnRepo.save(any(Transaction.class))).thenAnswer(i -> i.getArgument(0));

        Transaction t = service.withdraw("A", 50.0);

        assertEquals(150.0, acc.getBalance());
        assertEquals("WITHDRAW", t.getType());
    }

    @Test
    void testWithdrawInsufficientBalance() {
        Account acc = new Account();
        acc.setBalance(20.0);

        Mockito.when(accRepo.findByAccountNumber("A")).thenReturn(acc);

        assertThrows(InsufficientBalanceException.class,
                () -> service.withdraw("A", 50.0));
    }

    @Test
    void testTransferSuccess() {
        Account src = new Account();
        src.setBalance(300.0);
        Account dest = new Account();
        dest.setBalance(100.0);

        Mockito.when(accRepo.findByAccountNumber("S")).thenReturn(src);
        Mockito.when(accRepo.findByAccountNumber("D")).thenReturn(dest);
        Mockito.when(txnRepo.save(any(Transaction.class))).thenAnswer(i -> i.getArgument(0));

        Transaction t = service.transfer("S", "D", 100.0);

        assertEquals(200.0, src.getBalance());
        assertEquals(200.0, dest.getBalance());
        assertEquals("TRANSFER", t.getType());
    }

    @Test
    void testTransferInvalidAmount() {
        assertThrows(InvalidAmountException.class,
                () -> service.transfer("S", "D", -20.0));
    }

    @Test
    void testGetTransactions() {
        Transaction t1 = new Transaction();
        Transaction t2 = new Transaction();

        Mockito.when(txnRepo.findBySourceAccount("A")).thenReturn(List.of(t1));
        Mockito.when(txnRepo.findByDestinationAccount("A")).thenReturn(List.of(t2));

        List<Transaction> list = service.getTransactions("A");

        assertEquals(2, list.size());
    }
}
