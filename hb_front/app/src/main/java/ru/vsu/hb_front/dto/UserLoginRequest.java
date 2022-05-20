package ru.vsu.hb_front.dto;


public class UserLoginRequest {

    private String userEmail;

    private String password;

    public UserLoginRequest() {
    }

    public UserLoginRequest(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;
    }

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
