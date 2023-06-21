package com.tunadag.controller;

import com.tunadag.dto.request.AccountCreateRequestDto;
import com.tunadag.dto.request.AccountUpdateRequestDto;
import com.tunadag.repositories.entity.Account;
import com.tunadag.repositories.entity.Transaction;
import com.tunadag.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/getAccounts")
    @Operation(summary = "Kullanıcıya ait tüm hesaplar görüntülenir.")
    public List<Account> getUserAccounts() {
        return accountService.getUserAccounts();
    }

    @GetMapping("/{accountId}/transactions")
    @Operation(summary = "Kullanıcının belirli bir hesabına ait tüm işlemler görüntülenir.")
    public List<Transaction> getAccountTransactions(@PathVariable Long accountId) {
        return accountService.getAccountTransactions(accountId);
    }

    @PostMapping("/addAccount")
    @Operation(summary = "Kullanıcıya ait yeni bir hesap eklenir.")
    public ResponseEntity<Boolean> addAccount(@Valid @RequestBody AccountCreateRequestDto request) {
        return ResponseEntity.ok(accountService.addAccount(request));
    }

    @PutMapping("/update")
    @Operation(summary = "Kullanıcıya ait mevcut bir hesabın adı güncellenir.")
    public ResponseEntity<Boolean> updateAccountName(
            @RequestBody @Valid AccountUpdateRequestDto request) {
        return ResponseEntity.ok(accountService.updateAccountName(request));
    }

    @DeleteMapping("/deleteAccount")
    @Operation(summary = "Kullanıcıya ait bir hesap silinir.")
    public ResponseEntity<Boolean> deleteAccount(@RequestBody @Valid Long accountId) {
        return ResponseEntity.ok(accountService.delete(accountId));
    }

}
