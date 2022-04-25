package ru.vsu.hb.persistence.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.Collection;

@Entity
@Table(schema = "hb", name = "users")
@ApiModel(description = "Пользователь")
public class User implements UserDetails {

    @Id
    @Column(name = "user_email")
    @ApiModelProperty("Email пользователя")
    private String userEmail;

    @Column(name = "password")
    @ApiModelProperty("Пароль пользователя")
    private String password;

    @Column(name = "first_name")
    @ApiModelProperty("Имя пользователя")
    private String firstName;

    public User() {
    }

    public User(String email, String password, String firstName) {
        this.userEmail = email;
        this.password = password;
        this.firstName = firstName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


}
