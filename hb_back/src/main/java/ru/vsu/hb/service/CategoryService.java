package ru.vsu.hb.service;

import com.leakyabstractions.result.Result;
import com.leakyabstractions.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.hb.dto.CategoryDto;
import ru.vsu.hb.dto.error.BadRequestError;
import ru.vsu.hb.dto.error.EntityNotFoundError;
import ru.vsu.hb.dto.error.HBError;
import ru.vsu.hb.persistence.entity.Category;
import ru.vsu.hb.persistence.entity.UserCategoryId;
import ru.vsu.hb.persistence.repository.CategoryRepository;
import ru.vsu.hb.persistence.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    UserService userService;

    public Result<CategoryDto, HBError> getByUserCategoryId(String userEmail, UUID categoryID) {
        return userService.getUserDtoByEmail(userEmail).flatMapSuccess(u -> Results.ofCallable(() ->
                        repository.getByUserCategoryId(new UserCategoryId(u.getUserEmail(), categoryID))
                                .orElseThrow(() -> new IllegalStateException("not_found")))
                .mapSuccess(CategoryDto::fromEntity)
                .mapSuccess(it -> {
                    it.setOutSumLastMonth(repository.getSumForLastMonth(userEmail, it.getCategoryId()).orElse(BigDecimal.ZERO));
                    return it;
                })
                .mapFailure(exception -> {
                    if (exception instanceof IllegalStateException && "not_found".equals(exception.getMessage())) {
                        return new EntityNotFoundError("Category: " + categoryID + " and userEmail: " + u.getUserEmail() + " not found");
                    } else {
                        throw new RuntimeException(exception);
                    }
                }));
    }

    @Transactional
    public Result<CategoryDto, HBError> addCategory(CategoryDto category, String userEmail) {
        var categoryEntity = category.toEntity();
        categoryEntity.setUserCategoryId(new UserCategoryId(userEmail, category.getCategoryId()));
        return userService.getUserDtoByEmail(userEmail)
                .mapSuccess(it -> CategoryDto.fromEntity(repository.save(categoryEntity)));
    }

    @Transactional
    public Result<Integer, HBError> deleteByCategoryId(UUID categoryId, String userEmail) {
        return userService.getUserDtoByEmail(userEmail)
                .flatMapSuccess(u ->
                        Results.success(transactionRepository.deleteAllByCategoryIdAndUserEmail(categoryId, u.getUserEmail())))
                .mapSuccess(it -> repository.deleteByUserCategoryId_CategoryId(categoryId));
    }

    @Transactional
    public Result<CategoryDto, HBError> updateCategory(CategoryDto category, String userEmail) {
        if (category.getCategoryId() == null) {
            return Results.failure(new BadRequestError("Null value of identifier"));
        }
        return getByUserCategoryId(userEmail, category.getCategoryId())
                .mapSuccess(editCategory -> {
                    Category categoryEntity = new Category();
                    categoryEntity.setCategoryName(category.getCategoryName());
                    categoryEntity.setUserCategoryId(new UserCategoryId(userEmail, category.getCategoryId()));
                    categoryEntity.setDefault(editCategory.getDefault());
                    return repository.save(categoryEntity);
                }).mapSuccess(CategoryDto::fromEntity);

    }

    public Result<List<CategoryDto>, HBError> getAll(String userEmail) {
        return userService.getUserDtoByEmail(userEmail)
                .flatMapSuccess(u ->
                        Results.success(repository.getAllByUserCategoryId_UserEmail(u.getUserEmail())))
                .mapSuccess(it -> it.stream().map(CategoryDto::fromEntity)
                        .peek(category -> category.setOutSumLastMonth(repository.getSumForLastMonth(userEmail, category.getCategoryId())
                                .orElse(BigDecimal.ZERO)))
                        .collect(Collectors.toList()));
    }

}
