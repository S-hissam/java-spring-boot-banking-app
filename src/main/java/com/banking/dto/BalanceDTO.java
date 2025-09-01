package com.banking.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BalanceDTO {
    private String accountNumber;
    private BigDecimal balance;
    private LocalDateTime updatedAt;

    public BalanceDTO(String accountNumber, BigDecimal balance, LocalDateTime updatedAt) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.updatedAt = updatedAt;
    }
}