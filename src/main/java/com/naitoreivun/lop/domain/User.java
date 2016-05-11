package com.naitoreivun.lop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.naitoreivun.lop.domain.dto.SignupForm;
import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    @OneToMany(mappedBy = "user")
    private Set<UserGroup> userGroups;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Image> images;

    public User(String username, String password, String email, AppRole appRole) {
        this.username = username;
        this.password = encode(password);
        this.email = email;
        this.appRole = appRole;
        this.creationDate = DateTime.now();
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

    public Set<UserGroup> getUserGroups() {
        return userGroups;
    }

    public AppRole getAppRole() {
        return appRole;
    }

    public Set<Image> getImages() {
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
        List<String> roles = new ArrayList<>();
        if(AppRole.USER.equals(appRole.getRole())) {
            roles.add(AppRole.USER);
        } else if (AppRole.ADMIN.equals(appRole.getRole())) {
            roles.add(AppRole.USER);
            roles.add(AppRole.ADMIN);
        }
        return roles;
    }
}
