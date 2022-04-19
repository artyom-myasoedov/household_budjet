package ru.vsu.hb.dto;

import ru.vsu.hb.persistence.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionDto {

    private UUID transactionId;

    private UUID userId;

    private BigDecimal sum;

    private LocalDateTime createTime;

    private String description;

    private String categoryName;

    public TransactionDto() {
    }

    public TransactionDto(UUID transactionId, UUID userId, BigDecimal sum, LocalDateTime createTime, String description, String categoryName) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.sum = sum;
        this.createTime = createTime;
        this.description = description;
        this.categoryName = categoryName;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public UUID getUserId() {
        return userId;
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

    public String getCategoryName() {
        return categoryName;
    }

    public Transaction toEntity() {
        return new Transaction(transactionId,
                userId,
                sum,
                createTime,
                description,
                categoryName);
    }

    public static TransactionDto fromEntity(Transaction transaction) {
        return new TransactionDto(transaction.getTransactionId(),
                transaction.getUserId(),
                transaction.getSum(),
                transaction.getCreateTime(),
                transaction.getDescription(),
                transaction.getCategoryName());
    }
}
