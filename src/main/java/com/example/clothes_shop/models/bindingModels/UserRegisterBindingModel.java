package com.example.clothes_shop.models.bindingModels;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterBindingModel {

    @NotNull
    @Size(min = 3, max = 15)
    private String firstName;
    @NotNull
    @Size(min = 3, max = 15)
    private String lastName;
    @NotNull
    @Size(min = 3, max = 15)
    private String username;
    @NotNull
    @Size(min = 3, max = 15)
    private String password;
    @Size(min = 10, max = 10)
    @NotNull
    @Size(min = 10, max = 10)
    private String phoneNumber;

    @NotNull
    @Email
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
