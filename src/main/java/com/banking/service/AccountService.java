package com.banking.service;

import com.banking.dto.*;
import com.banking.entity.Account;
import com.banking.entity.Transaction;
import com.banking.exception.AccountNotFoundException;
import com.banking.exception.DuplicateAccountException;
import com.banking.exception.InsufficientBalanceException;
import com.banking.repository.AccountRepository;
import com.banking.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountDTO createAccount(CreateAccountRequest request) {
        if (accountRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateAccountException("Account with email already exists");
        }

        Account account = new Account();
        account.setAccountHolderName(request.getAccountHolderName());
        account.setEmail(request.getEmail());
        account.setPhoneNumber(request.getPhoneNumber());
        account.setAccountType(Account.AccountType.valueOf(request.getAccountType()));
        account.setBalance(request.getInitialDeposit() != null ?
                request.getInitialDeposit() : BigDecimal.ZERO);

        Account savedAccount = accountRepository.save(account);

        if (request.getInitialDeposit() != null && request.getInitialDeposit().compareTo(BigDecimal.ZERO) > 0) {
            createTransaction(savedAccount, Transaction.TransactionType.DEPOSIT,
                    request.getInitialDeposit(), "Initial Deposit");
        }

        return convertToDTO(savedAccount);
    }

    public AccountDTO getAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return convertToDTO(account);
    }

    public AccountDTO getAccountByNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return convertToDTO(account);
    }

    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AccountDTO> getActiveAccounts() {
        return accountRepository.findByIsActiveTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AccountDTO updateAccount(Long id, UpdateAccountRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (request.getAccountHolderName() != null) {
            account.setAccountHolderName(request.getAccountHolderName());
        }
        if (request.getPhoneNumber() != null) {
            account.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getAccountType() != null) {
            account.setAccountType(request.getAccountType());
        }

        Account updatedAccount = accountRepository.save(account);
        return convertToDTO(updatedAccount);
    }

    public TransactionDTO deposit(Long accountId, BigDecimal amount, String description) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        Transaction transaction = createTransaction(account, Transaction.TransactionType.DEPOSIT,
                amount, description);
        return convertTransactionToDTO(transaction);
    }

    public TransactionDTO withdraw(Long accountId, BigDecimal amount, String description) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        Transaction transaction = createTransaction(account, Transaction.TransactionType.WITHDRAWAL,
                amount, description);
        return convertTransactionToDTO(transaction);
    }

    public TransferResponseDTO transfer(TransferRequest request) {
        Account fromAccount = accountRepository.findByAccountNumber(request.getFromAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Source account not found"));
        Account toAccount = accountRepository.findByAccountNumber(request.getToAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Destination account not found"));

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

// Debit from source account
        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        Transaction debitTransaction = createTransaction(fromAccount,
                Transaction.TransactionType.TRANSFER_OUT, request.getAmount(),
                "Transfer to " + toAccount.getAccountNumber());

// Credit to destination account
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));
        Transaction creditTransaction = createTransaction(toAccount,
                Transaction.TransactionType.TRANSFER_IN, request.getAmount(),
                "Transfer from " + fromAccount.getAccountNumber());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        return new TransferResponseDTO(
                convertTransactionToDTO(debitTransaction),
                convertTransactionToDTO(creditTransaction),
                "Transfer successful"
        );
    }

    public BalanceDTO getBalance(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return new BalanceDTO(account.getAccountNumber(), account.getBalance(),
                account.getUpdatedAt());
    }

    public void deactivateAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        account.setIsActive(false);
        accountRepository.save(account);
    }

    private Transaction createTransaction(Account account, Transaction.TransactionType type,
                                          BigDecimal amount, String description) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionType(type);
        transaction.setAmount(amount);
        transaction.setBalanceAfterTransaction(account.getBalance());
        transaction.setDescription(description);
        transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
        return transactionRepository.save(transaction);
    }

    private AccountDTO convertToDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setAccountHolderName(account.getAccountHolderName());
        dto.setEmail(account.getEmail());
        dto.setPhoneNumber(account.getPhoneNumber());
        dto.setAccountType(account.getAccountType());
        dto.setBalance(account.getBalance());
        dto.setIsActive(account.getIsActive());
        dto.setCreatedAt(account.getCreatedAt());
        dto.setUpdatedAt(account.getUpdatedAt());
        return dto;
    }

    private TransactionDTO convertTransactionToDTO(Transaction transaction) {
        return getTransactionDTO(transaction);
    }
}