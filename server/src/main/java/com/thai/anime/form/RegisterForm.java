package com.thai.anime.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterForm {

    @NotBlank(message = "Please enter name.")
    private String name;

    @NotBlank(message = "Please enter email.")
    @Email(message = "Email is not valid.")
    private String email;

    @Size(min=6, max=30, message = "Must be at least 6 characters.")
    @NotBlank(message = "Please enter password.")
    private String password;
    @NotBlank(message = "Please enter password.")
    private String confirmPassword;

    public RegisterForm(String name, String email, String password, String confirmPassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
