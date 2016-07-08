package com.naitoreivun.lop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.naitoreivun.lop.domain.dto.SignupForm;
import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "validatePassword",
                procedureName = "validate_password",
                parameters = {
                        @StoredProcedureParameter(name = "password", type = String.class, mode = ParameterMode.IN),
                        @StoredProcedureParameter(name = "userId", type = Long.class, mode = ParameterMode.IN),
                        @StoredProcedureParameter(name = "isValid", type = Boolean.class, mode = ParameterMode.OUT)
                }
        )})

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @JsonIgnore
    @Column(nullable = false, length = 50)
    private String password;

    @Email
    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "app_role_id", nullable = false)
    private AppRole appRole;

    @Column(nullable = false)
    private DateTime creationDate;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserGroup> usersGroups;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Image> images;

    public User(String username, String password, String email, AppRole appRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.appRole = appRole;
        this.creationDate = DateTime.now();
        this.usersGroups = new ArrayList<>();
        this.images = new ArrayList<>();
    }

    public User(SignupForm signupForm, AppRole appRole) {
        this(signupForm.getUsername(), signupForm.getPassword(), signupForm.getEmail(), appRole);
    }

    public User() {
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

    public List<String> getRoles() {
        if (AppRole.USER.equals(appRole.getRole())) {
            return Collections.singletonList(AppRole.USER);
        } else if (AppRole.ADMIN.equals(appRole.getRole())) {
            return Arrays.asList(AppRole.USER, AppRole.ADMIN);
        }
        return Collections.emptyList();
    }
}
