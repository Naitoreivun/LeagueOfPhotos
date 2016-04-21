package com.naitoreivun.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
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

    @ManyToOne
    @JoinColumn(name = "app_role_id")
    private AppRole appRole;

    @OneToMany(mappedBy = "user")
    private Set<UserGroup> userGroups;

    @OneToMany(mappedBy = "user")
    private Set<Image> images;

    @OneToMany(mappedBy = "user")
    private Set<Vote> votes;

    public User(String username, String password, AppRole appRole) {
        this.username = username;
        this.password = encode(password);
        this.appRole = appRole;
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

    public Set<Vote> getVotes() {
        return votes;
    }
}
