package ru.vsu.hb.persistence.entity;


import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class UserCategoryId implements Serializable {

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "category_id")
    private UUID categoryId = UUID.randomUUID();

    public UserCategoryId(String userEmail, UUID categoryId) {
        this.userEmail = userEmail;
        this.categoryId = categoryId == null ? UUID.randomUUID() : categoryId;
    }

    public UserCategoryId() {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userId) {
        this.userEmail = userId;
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

        if (!Objects.equals(userEmail, that.userEmail)) return false;
        return Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        int result = userEmail != null ? userEmail.hashCode() : 0;
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        return result;
    }
}
