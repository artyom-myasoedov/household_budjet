package ru.vsu.hb.persistence.entity;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "transactions")
@Table(schema = "hb", name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id")
    private UUID transactionId;

    @ManyToOne
    @JoinColumn(
            name = "user_email",
            referencedColumnName = "user_email",
            insertable = false,
            updatable = false
    )
    private User user;

    @Column(name = "user_email", updatable = false)
    private String userEmail;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "create_time")
    @Generated(GenerationTime.INSERT)
    private LocalDateTime createTime;

    @Column(name = "description")
    private String description;

    @Column(name = "category_id")
    private UUID categoryId;

    public Transaction() {
    }

    public Transaction(UUID transactionId, String userEmail, BigDecimal sum, LocalDateTime createTime, String description, UUID categoryId) {
        this.transactionId = transactionId;
        this.userEmail = userEmail;
        this.sum = sum;
        this.createTime = createTime;
        this.description = description;
        this.categoryId = categoryId;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userId) {
        this.userEmail = userId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transaction_id) {
        this.transactionId = transaction_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
}
