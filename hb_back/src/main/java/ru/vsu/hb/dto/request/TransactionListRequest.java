package ru.vsu.hb.dto.request;

import ru.vsu.hb.dto.TransactionType;

import java.util.UUID;

public class TransactionListRequest {

    private UUID userId;
    private TransactionType transactionType = TransactionType.ALL;
    private PageRequest page = new PageRequest();

    public TransactionListRequest() {
    }

    public TransactionListRequest(UUID userId, TransactionType transactionType, PageRequest page) {
        this.userId = userId;
        this.transactionType = transactionType;
        this.page = page;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public PageRequest getPage() {
        return page;
    }

    public void setPage(PageRequest page) {
        this.page = page;
    }
}
