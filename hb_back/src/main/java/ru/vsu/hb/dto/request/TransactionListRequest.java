package ru.vsu.hb.dto.request;

import ru.vsu.hb.dto.TransactionType;

public class TransactionListRequest {

    private String userEmail;
    private TransactionType transactionType = TransactionType.ALL;
    private PageRequest page = new PageRequest();

    public TransactionListRequest() {
    }

    public TransactionListRequest(String userEmail, TransactionType transactionType, PageRequest page) {
        this.userEmail = userEmail;
        this.transactionType = transactionType;
        this.page = page;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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
