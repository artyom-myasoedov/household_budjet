package ru.vsu.hb.dto;

import ru.vsu.hb.persistence.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionDto {

    private UUID transactionId;

    private String userEmail;

    private BigDecimal sum;

    private LocalDateTime createTime;

    private String description;

    private UUID categoryId;

    public TransactionDto() {
    }

    public TransactionDto(UUID transactionId, String userEmail, BigDecimal sum, LocalDateTime createTime, String description, UUID categoryId) {
        this.transactionId = transactionId;
        this.userEmail = userEmail;
        this.sum = sum;
        this.createTime = createTime;
        this.description = description;
        this.categoryId = categoryId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public String getDescription() {
        return description;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public Transaction toEntity() {
        return new Transaction(transactionId,
                userEmail,
                sum,
                createTime,
                description,
                categoryId);
    }

    public static TransactionDto fromEntity(Transaction transaction) {
        return new TransactionDto(transaction.getTransactionId(),
                transaction.getUserEmail(),
                transaction.getSum(),
                transaction.getCreateTime(),
                transaction.getDescription(),
                transaction.getCategoryId());
    }
}
