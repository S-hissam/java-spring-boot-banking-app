package com.banking.dto;

import lombok.Data;

@Data
public class TransferResponseDTO {
    private TransactionDTO debitTransaction;
    private TransactionDTO creditTransaction;
    private String message;

    public TransferResponseDTO(TransactionDTO debit, TransactionDTO credit, String msg) {
        this.debitTransaction = debit;
        this.creditTransaction = credit;
        this.message = msg;
    }
}