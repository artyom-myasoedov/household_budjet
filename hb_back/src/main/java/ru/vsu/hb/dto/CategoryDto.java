package ru.vsu.hb.dto;

import ru.vsu.hb.persistence.entity.Category;
import ru.vsu.hb.persistence.entity.UserCategoryId;

import java.util.UUID;

public class CategoryDto {

    private UUID categoryId;
    private Boolean isDefault;
    private String categoryName;

    public CategoryDto(UUID categoryId, Boolean isDefault, String categoryName) {
        this.isDefault = isDefault;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
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
