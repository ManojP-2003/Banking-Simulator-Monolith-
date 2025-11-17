package com.bank.banking_monolith.controller;

import com.bank.banking_monolith.model.Transaction;
import com.bank.banking_monolith.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private TransactionService service;

    @Test
    void testDeposit() throws Exception {
        Mockito.when(service.deposit("A1", 100.0)).thenReturn(new Transaction());

        mvc.perform(put("/api/accounts/A1/deposit?amount=100"))
                .andExpect(status().isOk());
    }

    @Test
    void testWithdraw() throws Exception {
        Mockito.when(service.withdraw("A1", 50.0)).thenReturn(new Transaction());

        mvc.perform(put("/api/accounts/A1/withdraw?amount=50"))
                .andExpect(status().isOk());
    }

    @Test
    void testTransfer() throws Exception {
        Mockito.when(service.transfer("A1", "A2", 100.0)).thenReturn(new Transaction());

        mvc.perform(post("/api/accounts/transfer?sourceAccount=A1&destinationAccount=A2&amount=100"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTransactions() throws Exception {
        Mockito.when(service.getTransactions("A1")).thenReturn(List.of(new Transaction()));

        mvc.perform(get("/api/accounts/A1/transactions"))
                .andExpect(status().isOk());
    }
}
