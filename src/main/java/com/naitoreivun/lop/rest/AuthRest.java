package com.naitoreivun.lop.rest;

import com.naitoreivun.lop.MyProperties;
import com.naitoreivun.lop.dao.UserDAO;
import com.naitoreivun.lop.domain.User;
import com.naitoreivun.lop.domain.dto.SignupForm;
import com.naitoreivun.lop.security.LoginResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.util.Optional;

@RestController
@RequestMapping("")
public class AuthRest {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private MyProperties properties;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginResponse login(@RequestBody final SignupForm loginForm) throws ServletException {
        Optional<User> u = userDAO.findByUsername(loginForm.getUsername());// TODO: 2016-04-27 do not ignore case
        if (!u.isPresent()) {
            throw new ServletException("Invalid login");
        }

        User user = u.get();

        if (!user.validatePassword(loginForm.getPassword())) {
            throw new ServletException("Invalid password");
        }

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
