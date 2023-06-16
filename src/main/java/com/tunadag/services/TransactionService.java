package com.tunadag.services;

import com.tunadag.dto.request.TransactionAddRequestDto;
import com.tunadag.exceptions.custom.AccountNotFoundException;
import com.tunadag.exceptions.custom.TransactionNotFoundException;
import com.tunadag.repositories.AccountRepository;
import com.tunadag.repositories.TransactionRepository;
import com.tunadag.repositories.entity.Account;
import com.tunadag.repositories.entity.Transaction;
import com.tunadag.repositories.entity.User;
import com.tunadag.repositories.entity.enums.TransactionType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserService userService;
    private final AccountService accountService;
    @Transactional
    public Transaction addTransaction(TransactionAddRequestDto request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid account ID"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        try {
            date = dateFormat.parse(request.getDate());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format");
        }

        Transaction transaction = Transaction.builder()
                .date(date)
                .amount(Math.abs(request.getAmount()))
                .category(request.getCategory())
                .type(request.getType())
                .account(account)
                .build();

        // Eğer gider (expense) işlemi ise amount değerini negatif yap
        if (transaction.getType() == TransactionType.EXPENSE) {
            transaction.setAmount(-Math.abs(transaction.getAmount()));
        }

        transaction = transactionRepository.save(transaction);

        // Hesabın bakiyesini güncelle
        account.setBalance(account.getBalance() + transaction.getAmount());
        accountRepository.save(account);

        return transaction;
    }

    @Transactional
    public Boolean delete(Long transactionId, Long accountId) {
        User user = userService.getCurrentUser();

        Optional<Account> accountOptional = accountRepository.findActiveByOidAndUser(accountId, user);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Account not found or user is not authorized");
        }

        Account account = accountOptional.get();
        Optional<Transaction> transactionOptional = account.getTransactions().stream()
                .filter(transaction -> transaction.getOid().equals(transactionId))
                .findFirst();

        if (transactionOptional.isEmpty()) {
            throw new TransactionNotFoundException("Transaction not found or user is not authorized");
        }

        Transaction transaction = transactionOptional.get();
        transactionRepository.softDeleteById(transaction.getOid());
        return true;
    }
    public void saveAll(List<Transaction> transactions) {
        transactionRepository.saveAll(transactions);
    }

    public List<Transaction> searchTransactions(String query, int month, int year, Long accountId) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        // LocalDate'leri Date'lere dönüştür
        Date startDateAsDate = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDateAsDate = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Kullanıcının kimlik bilgisini al
        User user = userService.getCurrentUser();

        // Kullanıcının kendi accountlarını kontrol et
        Account account = accountService.getAccountByIdAndUser(accountId, user)
                .orElseThrow(() -> new IllegalArgumentException("Account not found or user is not authorized"));

        // Accountın transactionlarını al
        List<Transaction> accountTransactions = transactionRepository.findByAccount(account);

        // Arama yapılacak transactionları filtrele
        List<Transaction> filteredTransactions = accountTransactions.stream()
                .filter(transaction -> {
                    // Belirtilen dönem içinde olan transactionları seç
                    Date transactionDate = transaction.getDate();
                    return transactionDate.equals(startDateAsDate) || transactionDate.equals(endDateAsDate)
                            || (transactionDate.after(startDateAsDate) && transactionDate.before(endDateAsDate));
                })
                .filter(transaction -> transaction.getCategory().contains(query)
                        || String.valueOf(transaction.getAmount()).contains(query))
                .collect(Collectors.toList());

        return filteredTransactions;
    }

}
