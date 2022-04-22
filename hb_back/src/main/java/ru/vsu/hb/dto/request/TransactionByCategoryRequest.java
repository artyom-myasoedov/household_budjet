package ru.vsu.hb.dto.request;

import java.util.UUID;

public class TransactionByCategoryRequest {

    private String userEmail;
    private UUID categoryId;
    private PageRequest page = new PageRequest();

    public TransactionByCategoryRequest() {
    }

    public TransactionByCategoryRequest(String userEmail, UUID categoryId, PageRequest page) {
        this.userEmail = userEmail;
        this.categoryId = categoryId;
        this.page = page;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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
