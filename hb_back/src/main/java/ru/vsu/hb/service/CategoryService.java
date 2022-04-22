package ru.vsu.hb.service;

import com.leakyabstractions.result.Result;
import com.leakyabstractions.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.hb.dto.CategoryDto;
import ru.vsu.hb.dto.TransactionDto;
import ru.vsu.hb.dto.error.BadRequestError;
import ru.vsu.hb.dto.error.EntityNotFoundError;
import ru.vsu.hb.dto.error.HBError;
import ru.vsu.hb.dto.request.TransactionByCategoryRequest;
import ru.vsu.hb.persistence.entity.Category;
import ru.vsu.hb.persistence.entity.UserCategoryId;
import ru.vsu.hb.persistence.repository.CategoryRepository;
import ru.vsu.hb.persistence.repository.TransactionRepository;

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

    public Result<Category, HBError> getByUserCategoryId(UUID userId, UUID categoryID) {
        return Results.ofCallable(() ->
                repository.getByUserCategoryId(new UserCategoryId(userId, categoryID))
                        .orElseThrow(() -> new IllegalStateException("not_found")))
                .mapFailure(exception -> {
                    if (exception instanceof IllegalStateException && "not_found".equals(exception.getMessage())) {
                        return new EntityNotFoundError("Category: " + categoryID + " and userId: " + userId + " not found");
                    } else {
                        throw new RuntimeException(exception);
                    }
                });
    }

    public Result<Category, HBError> getByUserCategoryId(String userEmail, UUID categoryID) {

        return userService.findUserByEmail(userEmail).flatMapSuccess(u->Results.ofCallable(() ->
                repository.getByUserCategoryId(new UserCategoryId(u.getUserId(), categoryID))
                        .orElseThrow(() -> new IllegalStateException("not_found")))
                .mapFailure(exception -> {
                    if (exception instanceof IllegalStateException && "not_found".equals(exception.getMessage())) {
                        return new EntityNotFoundError("Category: " + categoryID + " and userId: " + u.getUserId() + " not found");
                    } else {
                        throw new RuntimeException(exception);
                    }
                }));
    }

    @Transactional
    public Result<CategoryDto, HBError> addCategory(CategoryDto category) {
        return Results.ofCallable(() ->
                CategoryDto.fromEntity(repository.save(category.toEntity())))
                .mapFailure(exception -> {
                    throw new RuntimeException(exception);
                });
    }

    @Transactional
    public Result<Integer, HBError> deleteByCategoryId(UUID categoryId, String userEmail) {
        return userService.findUserByEmail(userEmail)
                .flatMapSuccess(u ->
                        Results.success(transactionRepository.deleteAllByCategoryIdAndUserId(categoryId, u.getUserId())))
                .mapSuccess(it -> repository.deleteByUserCategoryId_CategoryId(categoryId));
    }

    @Transactional
    public Result<CategoryDto, HBError> updateCategory(CategoryDto category, String userEmail) {
        if (category.getCategoryId() == null) {
            return Results.failure(new BadRequestError("Null value of identifier"));
        }
        return getByUserCategoryId(userEmail, category.getCategoryId())
                .mapSuccess(editCategory -> {
                    editCategory.setCategoryName(category.getCategoryName());
                    return repository.save(editCategory);
                }).mapSuccess(CategoryDto::fromEntity);

    }

    public Result<List<CategoryDto>, HBError> getAll(String userEmail) {
        return userService.findUserByEmail(userEmail)
                .flatMapSuccess(u ->
                        Results.success(repository.getAllByUserCategoryId_UserId(u.getUserId())))
                .mapSuccess(it->it.stream().map(CategoryDto::fromEntity).collect(Collectors.toList()));
    }

}
