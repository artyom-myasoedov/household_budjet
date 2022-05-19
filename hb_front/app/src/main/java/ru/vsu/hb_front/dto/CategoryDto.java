package ru.vsu.hb_front.dto;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.UUID;

public class CategoryDto {

    private UUID categoryId;
    @SerializedName("default")
    private boolean isDefault;
    private String categoryName;
    private BigDecimal outSumLastMonth;

    public CategoryDto() {
    }

    public CategoryDto(UUID categoryId, boolean isDefault, String categoryName) {
        this.isDefault = isDefault;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }

    public BigDecimal getOutSumLastMonth() {
        return outSumLastMonth;
    }

    public void setOutSumLastMonth(BigDecimal outSumLastMonth) {
        this.outSumLastMonth = outSumLastMonth;
    }

    public boolean getDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

}
