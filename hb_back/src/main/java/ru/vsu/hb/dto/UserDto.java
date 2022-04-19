package ru.vsu.hb.dto;

import ru.vsu.hb.persistence.entity.User;

import java.util.UUID;

public class UserDto {

    private UUID userId;

    private String email;

    private String firstName;

    public UserDto() {
    }

    public UserDto(UUID userId, String email, String firstName) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public static UserDto fromEntity(User user) {
        return new UserDto(user.getUserId(), user.getEmail(), user.getFirstName());
    }
}
