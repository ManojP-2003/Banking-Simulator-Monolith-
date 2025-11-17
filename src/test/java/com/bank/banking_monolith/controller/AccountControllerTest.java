package com.bank.banking_monolith.controller;

import com.bank.banking_monolith.model.Account;
import com.bank.banking_monolith.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private AccountService service;

    @Test
    void testCreateAccount() throws Exception {
        Account a = new Account();
        a.setHolderName("Manoj");
        a.setAccountNumber("MAN5678");
        a.setBalance(0.0);
        a.setStatus("ACTIVE");
        a.setCreatedAt(new Date());

        Mockito.when(service.createAccount(Mockito.any())).thenReturn(a);

        mvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"holderName\":\"Manoj\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("MAN5678")) // Verify account number
                .andExpect(jsonPath("$.holderName").value("Manoj"));
    }

    @Test
    void testGetAccount() throws Exception {
        Account a = new Account();
        a.setAccountNumber("MAN5678");
        a.setHolderName("Manoj");
        a.setBalance(100.0);

        Mockito.when(service.getAccountByNumber("MAN5678")).thenReturn(a);

        mvc.perform(get("/api/accounts/MAN5678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("MAN5678"))
                .andExpect(jsonPath("$.holderName").value("Manoj"));
    }

    @Test
    void testGetAllAccounts() throws Exception {
        Mockito.when(service.getAllAccounts()).thenReturn(List.of(new Account()));

        mvc.perform(get("/api/accounts"))
                .andExpect(status().isOk());
    }
}
