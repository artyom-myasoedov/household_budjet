package ru.vsu.hb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vsu.hb.persistence.entity.Category;
import ru.vsu.hb.persistence.entity.UserCategoryId;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UserCategoryId> {

    Optional<Category> getByUserCategoryId(UserCategoryId userCategoryId);

    List<Category> getAllByUserCategoryId_UserEmail(String userEmail);

    @Query("select sum(t.sum) from transactions t where t.categoryId = :categoryId and t.userEmail = :userEmail and month(t.createTime) = month(current_timestamp())")
    Optional<BigDecimal> getSumForLastMonth(String userEmail, UUID categoryId);

    @Override
    Category save(Category category);

    Integer deleteByUserCategoryId_CategoryId(UUID categoryId);


}
