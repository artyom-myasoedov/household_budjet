package ru.vsu.hb.dto.request;

import java.util.UUID;

public class TransactionByCategoryRequest {

    private UUID userId;
    private String categoryName;
    private PageRequest page = new PageRequest();

    public TransactionByCategoryRequest() {
    }

    public TransactionByCategoryRequest(UUID userId, String categoryName, PageRequest page) {
        this.userId = userId;
        this.categoryName = categoryName;
        this.page = page;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public PageRequest getPage() {
        return page;
    }

    public void setPage(PageRequest page) {
        this.page = page;
    }
}
