package ru.vsu.hb.persistence.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(schema = "hb", name = "users_categories")
public class Category {

    @EmbeddedId
    private UserCategoryId userCategoryId;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Column(name = "category_name")
    private String categoryName;

    public Category() {
    }

    public Category(UserCategoryId userCategoryId, Boolean isDefault, String categoryName) {
        this.userCategoryId = userCategoryId;
        this.isDefault = isDefault;
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public UserCategoryId getUserCategoryId() {
        return userCategoryId;
    }

    public void setUserCategoryId(UserCategoryId userCategoryId) {
        this.userCategoryId = userCategoryId;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = Objects.requireNonNullElse(aDefault, false);
    }
}
