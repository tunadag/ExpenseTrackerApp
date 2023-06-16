package com.tunadag.utility;

import com.tunadag.repositories.entity.Account;
import com.tunadag.repositories.entity.Transaction;
import com.tunadag.repositories.entity.User;
import com.tunadag.repositories.entity.enums.Currency;
import com.tunadag.repositories.entity.enums.TransactionType;
import com.tunadag.services.AccountService;
import com.tunadag.services.TransactionService;
import com.tunadag.services.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataImpl {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserService userService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @PostConstruct
    public void loadData() {
        createUsers();
        createAccounts();
        createTransactions();
    }
    private void createUsers() {
        User user1 = User.builder()
                .email("tuna.dag@hotmail.com")
                .firstName("Tuna")
                .lastName("Dağ")
                .password(passwordEncoder.encode("stringst"))
                .build();

        User user2 = User.builder()
                .email("veli.tas@gmail.com")
                .firstName("Veli")
                .lastName("Tas")
                .password(passwordEncoder.encode("stringst"))
                .build();

        User user3 = User.builder()
                .email("ayse.bulut@gmail.com")
                .firstName("Ayse")
                .lastName("Bulut")
                .password(passwordEncoder.encode("stringst"))
                .build();

        userService.saveAll(List.of(user1, user2, user3));
    }

    private void createAccounts() {
        User user1 = userService.findByEmail("tuna.dag@hotmail.com");
        User user2 = userService.findByEmail("veli.tas@gmail.com");
        User user3 = userService.findByEmail("ayse.bulut@gmail.com");

        Account account1 = Account.builder()
                .user(user1)
                .name("Aylık harcama")
                .currency(Currency.TRY)
                .balance(9081.8)
                .build();

        Account account2 = Account.builder()
                .user(user1)
                .name("Dolar birikim")
                .currency(Currency.USD)
                .balance(650)
                .build();

        Account account3 = Account.builder()
                .user(user2)
                .name("Euro birikim")
                .currency(Currency.EUR)
                .balance(2400)
                .build();

        Account account4 = Account.builder()
                .user(user3)
                .name("Aylık harcama")
                .currency(Currency.TRY)
                .balance(32000)
                .build();

        accountService.saveAll(List.of(account1, account2, account3, account4));
    }

    private void createTransactions() {

        Account account1 = accountService.findByNameAndUser_Email("Aylık harcama", "tuna.dag@hotmail.com");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        Date date1 = dateFormat.parse("01.06.2023", new ParsePosition(0));
        Transaction transaction1 = Transaction.builder()
                .account(account1)
                .type(TransactionType.INCOME)
                .amount(10000.0)
                .category("Maaş ödemesi")
                .date(date1)
                .build();

        Date date2 = dateFormat.parse("12.06.2023", new ParsePosition(0));
        Transaction transaction2 = Transaction.builder()
                .account(account1)
                .type(TransactionType.EXPENSE)
                .amount(-668.0)
                .category("Market alışverişi")
                .date(date2)
                .build();

        Date date3 = dateFormat.parse("14.06.2023", new ParsePosition(0));
        Transaction transaction3 = Transaction.builder()
                .account(account1)
                .type(TransactionType.EXPENSE)
                .amount(-250.0)
                .category("Restoran ödemesi")
                .date(date3)
                .build();

        transactionService.saveAll(List.of(transaction1, transaction2, transaction3));
    }

}
