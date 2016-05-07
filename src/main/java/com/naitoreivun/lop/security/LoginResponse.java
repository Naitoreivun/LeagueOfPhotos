package com.naitoreivun.lop.security;

public class LoginResponse {
    private final String token;

    public LoginResponse(final String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
