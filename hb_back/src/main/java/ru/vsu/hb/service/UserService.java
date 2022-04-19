package ru.vsu.hb.service;

import com.leakyabstractions.result.Result;
import com.leakyabstractions.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.hb.dto.UserDto;
import ru.vsu.hb.dto.error.EntityNotFoundError;
import ru.vsu.hb.dto.error.HBError;
import ru.vsu.hb.persistence.entity.User;
import ru.vsu.hb.persistence.repository.UserRepository;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;


    public Result<UserDto, HBError> getById(UUID userId) {
        return Results.ofCallable(() -> repository.getByUserId(userId).orElseThrow(() -> new IllegalStateException("not_found")))
                .mapSuccess(UserDto::fromEntity)
                .mapFailure(e -> {
                    if ("not_found".equals(e.getMessage())) {
                        return new EntityNotFoundError("User with id = " + userId + " not found");
                    }
                    throw new RuntimeException(e);
                });
    }

}
