package com.tunadag.repositories;

import com.tunadag.repositories.base.BaseRepository;
import com.tunadag.repositories.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.state = 'ACTIVE' AND u.email = ?1")
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.state = 'ACTIVE' AND u.email = ?1")
    Optional<User> findOptionalByEmail(String username);
}
