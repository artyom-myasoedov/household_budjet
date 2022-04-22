package ru.vsu.hb.persistence.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class UserCategoryId implements Serializable {

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "category_id")
    private UUID categoryId = UUID.randomUUID();

    public UserCategoryId(UUID userId, UUID categoryId) {
        this.userId = userId;
        this.categoryId = categoryId == null ? UUID.randomUUID() : categoryId;
    }

    public UserCategoryId() {
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
        this.categoryId = categoryId == null ? UUID.randomUUID() : categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCategoryId that = (UserCategoryId) o;

        if (!Objects.equals(userId, that.userId)) return false;
        return Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        return result;
    }
}
