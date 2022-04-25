package ru.vsu.hb.service;

import com.leakyabstractions.result.Results;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.vsu.hb.dto.CategoryDto;
import ru.vsu.hb.dto.UserDto;
import ru.vsu.hb.persistence.entity.Category;
import ru.vsu.hb.persistence.entity.User;
import ru.vsu.hb.persistence.entity.UserCategoryId;
import ru.vsu.hb.persistence.repository.CategoryRepository;
import ru.vsu.hb.persistence.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

class CategoryServiceTest {

    private CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
    private TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);
    private UserService userService = Mockito.mock(UserService.class);
    private CategoryService categoryService =
            new CategoryService(categoryRepository, transactionRepository, userService);

    private User USER = new User("aaa@gmail.com", "12345", "Name");
    private UserCategoryId USER_CATEGORY_ID= new UserCategoryId(USER.getUserEmail(),
            UUID.fromString("ecd8c0d6-b513-4435-af7a-59ccbfcddf28"));
    private Category CATEGORY = new Category(USER_CATEGORY_ID, false, "new");

    @Test
    public void getByUserCategoryIdSuccess() {
        Mockito.when(userService.getUserDtoByEmail(any())).thenReturn(Results.success(new UserDto()));
        Mockito.when(categoryRepository.getByUserCategoryId(any())).thenReturn(Optional.of(CATEGORY));
        Mockito.when(categoryRepository.getSumForLastMonth(any(), any())).thenReturn(Optional.of(BigDecimal.ONE));
        var res = categoryService.getByUserCategoryId(USER.getUserEmail(), USER_CATEGORY_ID.getCategoryId());
        assertTrue(res.isSuccess());
    }

    @Test
    public void addCategorySuccess() {
        Mockito.when(userService.getUserDtoByEmail(any())).thenReturn(Results.success(new UserDto()));
        Mockito.when(categoryRepository.save(any())).thenReturn(CATEGORY);
        var res = categoryService.addCategory(CategoryDto.fromEntity(CATEGORY), USER.getUserEmail());
        assertTrue(res.isSuccess());
    }

    @Test
    public void deleteByCategoryIdSuccess() {
        Mockito.when(userService.getUserDtoByEmail(any())).thenReturn(Results.success(new UserDto()));
        Mockito.when(transactionRepository.deleteAllByCategoryIdAndUserEmail(USER_CATEGORY_ID.getCategoryId(), USER.getUserEmail()))
                .thenReturn(1);
        Mockito.when(categoryRepository.save(any())).thenReturn(CATEGORY);
        var res = categoryService.deleteByCategoryId(USER_CATEGORY_ID.getCategoryId(), USER.getUserEmail());
        assertTrue(res.isSuccess());
    }

    @Test
    public void getAllSuccess() {
        Mockito.when(userService.getUserDtoByEmail(any())).thenReturn(Results.success(new UserDto()));
        Mockito.when(categoryRepository.getAllByUserCategoryId_UserEmail(any())).thenReturn(List.of(CATEGORY));
        var res = categoryService.getAll(USER.getUserEmail());
        assertTrue(res.isSuccess());
    }


}