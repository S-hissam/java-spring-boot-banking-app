package com.banking.dto;


import com.banking.entity.Account;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountDTO {
    private Long id;
    private String accountNumber;
    private String accountHolderName;
    private String email;
    private String phoneNumber;
    private Account.AccountType accountType;
    private BigDecimal balance;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}