package com.naitoreivun.lop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.naitoreivun.lop.domain.dto.SignupForm;
import org.hibernate.mapping.Array;
import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    @Email
    private String email;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "app_role_id")
    private AppRole appRole;

    private DateTime creationDate;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserGroup> usersGroups;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Image> images;

    public User(String username, String password, String email, AppRole appRole) {
        this.username = username;
        this.password = encode(password);
        this.email = email;
        this.appRole = appRole;
        this.creationDate = DateTime.now();
        this.usersGroups = new ArrayList<>();
    }

    public User(SignupForm signupForm, AppRole appRole) {
        this(signupForm.getUsername(), signupForm.getPassword(), signupForm.getEmail(), appRole);
    }

    public User() {
    }

    private String encode(String text) { //Szyfr cezara +4 :DDDD
        return text
                .chars()
                .mapToObj(i -> (char) (i + 4))
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<UserGroup> getUsersGroups() {
        return usersGroups;
    }

    public AppRole getAppRole() {
        return appRole;
    }

    public List<Image> getImages() {
        return images;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public String getEmail() {
        return email;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(encode(password));
    }

    public List<String> getRoles() {
        if (AppRole.USER.equals(appRole.getRole())) {
            return Collections.singletonList(AppRole.USER);
        } else if (AppRole.ADMIN.equals(appRole.getRole())) {
            return Arrays.asList(AppRole.USER, AppRole.ADMIN);
        }
        return Collections.emptyList();
    }

}
