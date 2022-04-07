package ru.vsu.hb.persistence.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(schema = "hb", name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private UUID user_id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;


    @Column(name = "first_name")
    private String firstName;

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
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
