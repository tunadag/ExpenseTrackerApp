package com.tunadag.services;

import com.tunadag.dto.request.AccountCreateRequestDto;
import com.tunadag.dto.request.AccountUpdateRequestDto;
import com.tunadag.exceptions.custom.AccountNotFoundException;
import com.tunadag.exceptions.custom.TransactionNotFoundException;
import com.tunadag.repositories.AccountRepository;
import com.tunadag.repositories.TransactionRepository;
import com.tunadag.repositories.entity.Account;
import com.tunadag.repositories.entity.Transaction;
import com.tunadag.repositories.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, UserService userService,
                          @Qualifier("transactionRepository") TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.transactionRepository = transactionRepository;
    }

    public List<Account> getUserAccounts() {
        User user = userService.getCurrentUser();
        return accountRepository.findByUserOid(user.getOid());
    }

    public Boolean addAccount(AccountCreateRequestDto request) {
        User user = userService.getCurrentUser();

        Account account = Account.builder()
                .name(request.getName())
                .currency(request.getCurrency())
                .user(user)
                .build();

        accountRepository.save(account);
        return true;
    }
    @Transactional
    public Boolean updateAccountName(AccountUpdateRequestDto request) {
        User user = userService.getCurrentUser();

        Optional<Account> accountToUpdate = accountRepository.findActiveByOidAndUser(request.getAccountOid(), user);
        if (accountToUpdate.isEmpty()) {
            throw new AccountNotFoundException("Account is not found to update");
        } else {
            accountToUpdate.get().setName(request.getAccountName());
            Account account = accountToUpdate.get();
            accountRepository.save(account);
        }
        return true;
    }
    @Transactional
    public Boolean delete(Long accountId) {
        User user = userService.getCurrentUser();

        Optional<Account> accountOptional = accountRepository.findActiveByOidAndUser(accountId, user);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Account not found or user is not authorized");
        }

        Account account = accountOptional.get();
        Optional<Transaction> transactionOptional = transactionRepository.findActiveByOidAndAccount(accountId, account);
        if (transactionOptional.isEmpty()) {
            throw new TransactionNotFoundException("Transaction not found or user is not authorized");
        }
        Transaction transaction = transactionOptional.get();
        transactionRepository.softDeleteById(transaction.getOid());
        return true;
    }

    public List<Transaction> getAccountTransactions(Long accountOid) {
        User user = userService.getCurrentUser();

        Account account = accountRepository.findByOidAndUser(accountOid, user)
                .orElseThrow(() -> new IllegalArgumentException("Invalid attempt"));
        return account.getTransactions();
    }

    public void saveAll(List<Account> accounts) {
        accountRepository.saveAll(accounts);
    }

    public Account findByNameAndUser_Email(String name, String userEmail) {
        return accountRepository.findByNameAndUser_Email(name, userEmail);
    }
    public Optional<Account> getAccountByIdAndUser(Long accountId, User user) {
        return accountRepository.findByOidAndUser(accountId, user);
    }
}
