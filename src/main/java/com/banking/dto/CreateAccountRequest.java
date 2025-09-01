package com.banking.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateAccountRequest {
    private String accountHolderName;
    private String email;
    private String phoneNumber;
    private String accountType;
    private BigDecimal initialDeposit;
}