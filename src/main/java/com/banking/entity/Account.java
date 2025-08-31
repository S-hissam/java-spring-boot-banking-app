package com.banking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @NotBlank(message = "Account holder name is required")
    @Size(min = 2, max = 100)
    @Column(nullable = false)
    private String accountHolderName;

    @Email(message = "Email should be valid")
    @Column(unique = true, nullable = false)
    private String email;

    @Pattern(regexp = "^\\+?[1-9]\\d{9,14}$", message = "Phone number is invalid")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType = AccountType.SAVINGS;

    @DecimalMin(value = "0.0", inclusive = true)
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean isActive = true;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (accountNumber == null) {
            accountNumber = generateAccountNumber();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis();
    }

    public enum AccountType {
        SAVINGS, CURRENT, FIXED_DEPOSIT
    }

}