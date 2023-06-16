package com.tunadag.repositories;

import com.tunadag.repositories.base.BaseRepository;
import com.tunadag.repositories.entity.Account;
import com.tunadag.repositories.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends BaseRepository<Account, Long> {
    List<Account> findByUserOid(Long userId);

    Optional<Account> findByOidAndUser(Long accountOid, User user);
    Account findByNameAndUser_Email(String name, String userEmail);
    Optional<Account> findActiveByOidAndUser(Long accountOid, User user);
}
