package com.thai.anime.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoginForm {

    @Email(message = "Email is not valid.")
    @NotBlank(message = "Please enter email.")
    private String email;
    @NotBlank(message = "Please enter password.")
    private String password;

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
}
