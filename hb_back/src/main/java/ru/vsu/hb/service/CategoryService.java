package ru.vsu.hb.service;

import com.leakyabstractions.result.Result;
import com.leakyabstractions.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.hb.dto.error.EntityNotFoundError;
import ru.vsu.hb.dto.error.HBError;
import ru.vsu.hb.persistence.entity.Category;
import ru.vsu.hb.persistence.entity.UserCategoryId;
import ru.vsu.hb.persistence.repository.CategoryRepository;

import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Result<Category, HBError> getByUserCategoryId(UUID userId, String categoryName) {
        return Results.ofCallable(() ->
                        repository.getByUserCategoryId(new UserCategoryId(userId, categoryName))
                                .orElseThrow(() -> new IllegalStateException("not_found")))
                .mapFailure(exception -> {
                    if (exception instanceof IllegalStateException && "not_found".equals(exception.getMessage())) {
                        return new EntityNotFoundError("Category: " + categoryName + " and userId: " + userId + " not found");
                    } else {
                        throw new RuntimeException(exception);
                    }
                });
    }
}
