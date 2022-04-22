package ru.vsu.hb.persistence.entity;

import javax.persistence.*;

@Entity
@Table(schema = "hb", name = "users_categories")
public class Category {

    @EmbeddedId
    private UserCategoryId userCategoryId;

    @Column(name = "is_default")
    private Boolean isDefault;

    private String categoryName;

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
        isDefault = aDefault;
    }
}
