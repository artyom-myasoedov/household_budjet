package ru.vsu.hb.service;

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

    private User USER = new User("aaa@gmail.com", "12345", "Name");

    @Test
    public void createUserSuccess() {
        Mockito.when(repository.save(any())).thenReturn(USER);
        var res = userService.createUser(USER);
        assertEquals(res.getUserEmail(), USER.getUserEmail());
    }

    @Test
    public void editUserSuccess(){
        Mockito.when(repository.findByUserEmail(any())).thenReturn(Optional.of(USER));
        Mockito.when(repository.save(any())).thenReturn(USER);
        var res = userService.editUser(USER);
        assertTrue(res.isSuccess());
    }

    @Test
    public void getUserByEmailSuccess(){
        Mockito.when(repository.findByUserEmail(any())).thenReturn(Optional.of(USER));
        var res = userService.getUserByEmail(USER.getUserEmail());
        assertTrue(res.isSuccess());
    }

    @Test
    public void getUserDtoByEmailSuccess(){
        Mockito.when(repository.findByUserEmail(any())).thenReturn(Optional.of(USER));
        var res = userService.getUserDtoByEmail(USER.getUserEmail());
        assertTrue(res.isSuccess());
    }

}