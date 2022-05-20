package ru.vsu.hb_front.dto;


public class UserEditRequest {

    private String userEmail;

    private String password;

    private String firstName;

    public UserEditRequest() {
    }

    public UserEditRequest(String userEmail, String password, String firstName) {
        this.userEmail = userEmail;
        this.password = password;
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
