package com.naitoreivun.lop.rest;

import com.naitoreivun.lop.MyProperties;
import com.naitoreivun.lop.domain.User;
import com.naitoreivun.lop.domain.dto.SignupForm;
import com.naitoreivun.lop.security.LoginResponse;
import com.naitoreivun.lop.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;

@RestController
@RequestMapping("")
public class AuthRest {

    @Autowired
    private UserService userService;

    @Autowired
    private MyProperties properties;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginResponse login(@RequestBody final SignupForm loginForm) throws ServletException {
        User user = userService.getByUsername(loginForm.getUsername());
        userService.validatePassword(loginForm.getPassword(), user.getId());

        final String token = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("id", user.getId())
                .claim("roles", user.getRoles())
                .setIssuedAt(new DateTime().toDate())
                .setExpiration(new DateTime().plusDays(1).toDate())
                .signWith(SignatureAlgorithm.HS256, properties.getSecretKey())
                .compact();

        return new LoginResponse(token);
    }
}
