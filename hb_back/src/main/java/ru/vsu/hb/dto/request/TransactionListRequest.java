package ru.vsu.hb.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ru.vsu.hb.dto.TransactionType;

@ApiModel(description = "Запрос списка транзакций пользователя")
public class TransactionListRequest {

    @ApiModelProperty("Email пользователя")
    private String userEmail;
    @ApiModelProperty("Тип транзакции(пополнение, расход ил все)")
    private TransactionType transactionType = TransactionType.ALL;
    @ApiModelProperty("Пагинация")
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
