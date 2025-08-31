package com.banking.repository;

import com.banking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    Optional<Account> findByEmail(String email);

    List<Account> findByIsActiveTrue();

    List<Account> findByAccountHolderNameContainingIgnoreCase(String name);

    @Query("SELECT a FROM Account a WHERE a.accountType = ?1 AND a.isActive = true")
    List<Account> findActiveAccountsByType(Account.AccountType accountType);

    boolean existsByEmail(String email);

    boolean existsByAccountNumber(String accountNumber);
}