package ru.vsu.hb.dto;

import ru.vsu.hb.persistence.entity.User;

public class UserDto {

    private String userEmail;

    private String firstName;

    public UserDto() {
    }

    public UserDto(String email, String firstName) {
        this.userEmail = email;
        this.firstName = firstName;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public String getFirstName() {
        return firstName;
    }

    public static UserDto fromEntity(User user) {
        return new UserDto(user.getUserEmail(), user.getFirstName());
    }
}
