package com.naitoreivun.lop.domain.dto;

public class SignupForm extends LoginForm {
    private String email;
    private String confirmPassword;

    public SignupForm(String username, String password, String email, String confirmPassword) {
        super(username, password);
        this.email = email;
        this.confirmPassword = confirmPassword;
    }

    public SignupForm() {
    }

    public String getEmail() {
        return email;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public boolean isValid() {
        // TODO: 2016-04-24 email validation
        return confirmPassword.equals(getPassword());
    }
}
