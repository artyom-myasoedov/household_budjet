package ru.vsu.hb.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ru.vsu.hb.persistence.entity.Category;
import ru.vsu.hb.persistence.entity.UserCategoryId;

import java.math.BigDecimal;
import java.util.UUID;

@ApiModel(description = "Категория")
public class CategoryDto {

    @ApiModelProperty(value = "Идентификатор категории")
    private UUID categoryId;
    @ApiModelProperty(value = "Признак является категория категорией по умолчанию")
    private Boolean isDefault;
    @ApiModelProperty(value = "Название категории")
    private String categoryName;
    @ApiModelProperty(value = "Сумма расходов на категорию в текущем месяце")
    private BigDecimal outSumLastMonth;

    public CategoryDto() {
    }

    public CategoryDto(UUID categoryId, Boolean isDefault, String categoryName) {
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

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
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

    public Category toEntity() {
        UserCategoryId userCategoryId = new UserCategoryId();
        userCategoryId.setCategoryId(categoryId);
        Category category = new Category();
        category.setUserCategoryId(userCategoryId);
        category.setDefault(isDefault);
        category.setCategoryName(categoryName);
        return category;
    }

    public static CategoryDto fromEntity(Category category) {
        return new CategoryDto(category.getUserCategoryId().getCategoryId(),
                category.getDefault(),
                category.getCategoryName());
    }
}
