package com.tunadag.controller;

import com.tunadag.dto.request.TransactionAddRequestDto;
import com.tunadag.dto.request.TransactionDeleteRequestDto;
import com.tunadag.repositories.entity.Transaction;
import com.tunadag.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @PostMapping("/add")
    @Operation(summary = "Kullanıcıya ait belirli bir hesaba işlem eklenir.",
    description = "Tarihi GG/AA/YYYY formatında giriniz.")
    public ResponseEntity<Transaction> addTransaction(@RequestBody TransactionAddRequestDto request) {
        return ResponseEntity.ok(transactionService.addTransaction(request));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Kullanıcıya ait belirli bir hesaptan bir işlem silinir.")
    public ResponseEntity<Boolean> deleteTransaction(@RequestBody TransactionDeleteRequestDto request) {
        return ResponseEntity.ok(transactionService.delete(request.getAccountId(), request.getTransactionId()));
    }

    @GetMapping("/search")
    @Operation(summary = "Kullanıcıya ait belirli bir hesapta, belirli bir döneme(ay/yıl) ait " +
            "işlemler içinde tutar ya da kategori ismine göre arama yapılır.")
    public List<Transaction> searchTransactions(@RequestParam("accountId") Long accountId,
                                                @RequestParam("query") String query,
                                                @RequestParam("month") int month,
                                                @RequestParam("year") int year) {

        return transactionService.searchTransactions(query, month, year, accountId);
    }

}
