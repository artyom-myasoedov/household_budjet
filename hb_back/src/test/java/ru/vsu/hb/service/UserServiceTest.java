package ru.vsu.hb.service;

import com.leakyabstractions.result.Results;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.vsu.hb.dto.UserDto;
import ru.vsu.hb.persistence.entity.User;
import ru.vsu.hb.persistence.repository.UserRepository;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class UserServiceTest {

    private UserRepository repository = Mockito.mock(UserRepository.class);
    private UserService userService = new UserService(repository);
    private CategoryService categoryService = Mockito.mock(CategoryService.class);

    private User USER = new User("aaa@gmail.com", "12345", "Name");

    private UserDto USER_DTO = UserDto.fromEntity(USER);

    @Test
    public void createUserSuccess() {
        Mockito.when(repository.save(any())).thenReturn(USER);
        var res = userService.createUser(USER);
        assertNotNull(res.getUserEmail());
    }

    @Test
    public void editUserSuccess(){
        Mockito.when(repository.findByUserEmail(USER.getUserEmail())).thenReturn(Optional.of(USER));
        Mockito.when(userService.getUserByEmail(USER.getUserEmail())).thenReturn(Results.success(USER));
        Mockito.when(userService.editUser(any())).thenReturn(Results.success(USER_DTO));
        var res = userService.editUser(USER);
        assertTrue(res.isSuccess());
    }


}