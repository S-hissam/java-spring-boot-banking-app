package com.banking.repository;

import com.banking.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionId(String transactionId);

    List<Transaction> findByAccountIdOrderByTransactionDateDesc(Long accountId);

    Page<Transaction> findByAccountId(Long accountId, Pageable pageable);

    List<Transaction> findByAccountIdAndTransactionDateBetween(
            Long accountId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT t FROM Transaction t WHERE t.account.accountNumber = ?1 ORDER BY t.transactionDate DESC")
    List<Transaction> findByAccountNumber(String accountNumber);

    List<Transaction> findByStatus(Transaction.TransactionStatus status);
}