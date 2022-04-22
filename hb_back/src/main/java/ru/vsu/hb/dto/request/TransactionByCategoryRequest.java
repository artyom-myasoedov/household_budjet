package ru.vsu.hb.dto.request;

import java.util.UUID;

public class TransactionByCategoryRequest {

    private UUID userId;
    private UUID categoryId;
    private PageRequest page = new PageRequest();

    public TransactionByCategoryRequest() {
    }

    public TransactionByCategoryRequest(UUID userId, UUID categoryId, PageRequest page) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.page = page;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public PageRequest getPage() {
        return page;
    }

    public void setPage(PageRequest page) {
        this.page = page;
    }
}
