package com.naitoreivun.lop.security;

import io.jsonwebtoken.Claims;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class ClaimGetter {

    public static Long getCurrentUserId(final HttpServletRequest request) throws ServletException {
        final Claims claims = (Claims) request.getAttribute("claims");
        return ((Integer) claims.get("id")).longValue();
    }
}
