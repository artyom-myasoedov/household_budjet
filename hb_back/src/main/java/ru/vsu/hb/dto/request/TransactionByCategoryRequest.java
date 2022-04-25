package ru.vsu.hb.dto.request;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

@ApiModel(description = "Запрос списка транзакций по категории")
public class TransactionByCategoryRequest {

    @ApiModelProperty("Email пользователя")
    private String userEmail;
    @ApiModelProperty("Идентификатор категории")
    private UUID categoryId;
    @ApiModelProperty("Пагинация")
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
