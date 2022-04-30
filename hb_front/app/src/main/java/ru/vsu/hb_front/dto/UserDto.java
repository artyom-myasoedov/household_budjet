package ru.vsu.hb_front.dto;


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


}
