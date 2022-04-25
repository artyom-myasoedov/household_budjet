package ru.vsu.hb.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ru.vsu.hb.persistence.entity.User;

@ApiModel(description = "Пользователь")
public class UserDto {

    @ApiModelProperty(value = "Email пользователя")
    private String userEmail;

    @ApiModelProperty(value = "Имя пользователя")
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
