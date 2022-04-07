package ru.vsu.hb.persistence.entity;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class UserCategoryId implements Serializable {

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "category_name")
    private String categoryName;

    public UserCategoryId(UUID userId, String categoryName) {
        this.userId = userId;
        this.categoryName = categoryName;
    }

    public UserCategoryId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCategoryId that = (UserCategoryId) o;

        if (!Objects.equals(userId, that.userId)) return false;
        return Objects.equals(categoryName, that.categoryName);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        return result;
    }
}
