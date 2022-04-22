package ru.vsu.hb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.hb.persistence.entity.Category;
import ru.vsu.hb.persistence.entity.UserCategoryId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UserCategoryId> {

    Optional<Category> getByUserCategoryId(UserCategoryId userCategoryId);

    List<Category> getAllByUserCategoryId_UserId(UUID userId);

    @Override
    Category save(Category category);

    Integer deleteByUserCategoryId_CategoryId(UUID categoryId);

    void deleteByUserCategoryId_UserId(UUID userId);





}
