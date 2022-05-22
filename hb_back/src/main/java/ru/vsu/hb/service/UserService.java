package ru.vsu.hb.service;

import com.leakyabstractions.result.Result;
import com.leakyabstractions.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vsu.hb.dto.CategoryDto;
import ru.vsu.hb.dto.DefaultCategory;
import ru.vsu.hb.dto.UserDto;
import ru.vsu.hb.dto.error.EntityNotFoundError;
import ru.vsu.hb.dto.error.HBError;
import ru.vsu.hb.persistence.entity.User;
import ru.vsu.hb.persistence.repository.CategoryRepository;
import ru.vsu.hb.persistence.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Result<User, HBError> getUserByEmail(String userEmail) {
        return Results.ofCallable(() -> repository.findByUserEmail(userEmail).orElseThrow(() -> new IllegalStateException("not_found")))
                .mapFailure(e -> {
                    if ("not_found".equals(e.getMessage())) {
                        return new EntityNotFoundError("User with email = " + userEmail + " not found");
                    }
                    throw new RuntimeException(e);
                });
    }

    public Result<UserDto, HBError> editUser(User user) {
        return getUserByEmail(user.getUserEmail())
                .mapSuccess(userInDb -> {
                    userInDb.setFirstName(user.getFirstName() == null ? userInDb.getFirstName() : user.getFirstName());
                    if (user.getPassword() != null) {
                        userInDb.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                    }
                    return repository.save(userInDb);
                })
                .mapSuccess(UserDto::fromEntity);
    }

    public Result<UserDto, HBError> getUserDtoByEmail(String userEmail) {
        return Results.ofCallable(() -> repository.findByUserEmail(userEmail).orElseThrow(() -> new IllegalStateException("not_found")))
                .mapSuccess(UserDto::fromEntity)
                .mapFailure(e -> {
                    if ("not_found".equals(e.getMessage())) {
                        return new EntityNotFoundError("User with email = " + userEmail + " not found");
                    }
                    throw new RuntimeException(e);
                });
    }

    public User createUser(User user) {
        return repository.save(user);
    }

}
