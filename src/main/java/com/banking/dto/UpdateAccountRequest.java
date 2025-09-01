package com.banking.dto;

import lombok.Data;

@Data
public class UpdateAccountRequest {
    private String accountHolderName;
    private String phoneNumber;
    private String accountType;
}