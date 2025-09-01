package com.banking.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.banking.entity.Transaction.TransactionType;
import com.banking.entity.Transaction.TransactionStatus;

@Data
public class TransactionDTO {
    private Long id;
    private String transactionId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private BigDecimal balanceAfterTransaction;
    private String description;
    private LocalDateTime transactionDate;
    private TransactionStatus status;
}