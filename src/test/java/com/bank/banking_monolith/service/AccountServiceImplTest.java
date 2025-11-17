package com.bank.banking_monolith.service;

import com.bank.banking_monolith.exception.AccountNotFoundException;
import com.bank.banking_monolith.exception.InvalidAmountException;
import com.bank.banking_monolith.model.Account;
import com.bank.banking_monolith.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class AccountServiceImplTest {

    private AccountServiceImpl service;
    private AccountRepository repo;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(AccountRepository.class);
        service = new AccountServiceImpl();
        service.accountRepository = repo;
    }

    @Test
    void testCreateAccountSuccess() {
        Account req = new Account();
        req.setHolderName("Manoj");

        Mockito.when(repo.save(any(Account.class))).thenAnswer(i -> i.getArgument(0));

        Account created = service.createAccount(req);

        assertNotNull(created.getAccountNumber());
        assertEquals(0.0, created.getBalance());
        assertEquals("ACTIVE", created.getStatus());
        assertNotNull(created.getCreatedAt());
    }

    @Test
    void testCreateAccountEmptyName() {
        Account req = new Account();
        req.setHolderName("");

        assertThrows(InvalidAmountException.class,
                () -> service.createAccount(req));
    }

    @Test
    void testGetAccount() {
        Account acc = new Account();
        acc.setAccountNumber("A123");
        Mockito.when(repo.findByAccountNumber("A123")).thenReturn(acc);

        Account found = service.getAccountByNumber("A123");
        assertEquals("A123", found.getAccountNumber());
    }

    @Test
    void testAccountNotFound() {
        Mockito.when(repo.findByAccountNumber("XYZ")).thenReturn(null);

        assertThrows(AccountNotFoundException.class,
                () -> service.getAccountByNumber("XYZ"));
    }

    @Test
    void testGetAllAccounts() {
        Mockito.when(repo.findAll()).thenReturn(List.of(new Account(), new Account()));

        List<Account> list = service.getAllAccounts();
        assertEquals(2, list.size());
    }

    @Test
    void testDeleteAccount() {
        Account acc = new Account();
        Mockito.when(repo.findByAccountNumber("A123")).thenReturn(acc);

        assertDoesNotThrow(() -> service.deleteAccount("A123"));
    }

    @Test
    void testDeleteAccountNotFound() {
        Mockito.when(repo.findByAccountNumber("X")).thenReturn(null);

        assertThrows(AccountNotFoundException.class,
                () -> service.deleteAccount("X"));
    }
}
