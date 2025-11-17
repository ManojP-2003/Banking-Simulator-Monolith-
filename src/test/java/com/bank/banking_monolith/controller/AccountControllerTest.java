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
        Mockito.when(service.createAccount(Mockito.any())).thenReturn(a);

        mvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"holderName\":\"Manoj\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAccount() throws Exception {
        Account a = new Account();
        a.setAccountNumber("A1");
        Mockito.when(service.getAccountByNumber("A1")).thenReturn(a);

        mvc.perform(get("/api/accounts/A1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllAccounts() throws Exception {
        Mockito.when(service.getAllAccounts()).thenReturn(List.of(new Account()));

        mvc.perform(get("/api/accounts"))
                .andExpect(status().isOk());
    }
}
