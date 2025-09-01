package com.banking.service;

import com.banking.dto.TransactionDTO;
import com.banking.entity.Account;
import com.banking.entity.Transaction;
import com.banking.exception.AccountNotFoundException;
import com.banking.exception.InvalidTransactionException;
import com.banking.repository.AccountRepository;
import com.banking.repository.TransactionRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<TransactionDTO> getTransactionsByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountNumber));
        List<Transaction> transactions = transactionRepository.findAll()
                .stream()
                .filter(t -> t.getAccount().getId().equals(account.getId()))
                .toList();
        return transactions.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public TransactionDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new InvalidTransactionException("Transaction not found: " + id));
        return toDTO(transaction);
    }

    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Account account = accountRepository.findByAccountNumber(transactionDTO.getTransactionId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found for transaction."));
        Transaction transaction = new Transaction(
                null,
                transactionDTO.getTransactionId(),
                account,
                transactionDTO.getTransactionType(),
                transactionDTO.getAmount(),
                account.getBalance().add(transactionDTO.getAmount()),
                transactionDTO.getDescription(),
                transactionDTO.getTransactionDate(),
                transactionDTO.getStatus(),
                null
        );
        Transaction saved = transactionRepository.save(transaction);
        return toDTO(saved);
    }

    private TransactionDTO toDTO(Transaction transaction) {
        return getTransactionDTO(transaction);
    }

    static TransactionDTO getTransactionDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setTransactionId(transaction.getTransactionId());
        dto.setTransactionType(transaction.getTransactionType());
        dto.setAmount(transaction.getAmount());
        dto.setBalanceAfterTransaction(transaction.getBalanceAfterTransaction());
        dto.setDescription(transaction.getDescription());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setStatus(transaction.getStatus());
        return dto;
    }
}