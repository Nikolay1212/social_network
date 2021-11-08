package com.example.socialnetwork.repository;

import com.example.socialnetwork.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {
    Account findByFirstName(String firstName);
}
