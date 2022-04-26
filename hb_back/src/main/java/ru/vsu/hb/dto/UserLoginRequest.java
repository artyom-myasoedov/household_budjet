package ru.vsu.hb.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "Запрос авторизации")
public class UserLoginRequest {

    @ApiModelProperty(value = "Email пользователя")
    private String userEmail;

    @ApiModelProperty("Пароль пользователя")
    private String password;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
