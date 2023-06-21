package com.tunadag.repositories;

import com.tunadag.repositories.base.BaseRepository;
import com.tunadag.repositories.entity.Account;
import com.tunadag.repositories.entity.Transaction;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction, Long> {
    List<Transaction> findByAccount(Account account);
}
