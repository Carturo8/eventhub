package com.carturo.eventhub.domain.model.user;

import java.util.HashSet;
import java.util.Set;

public class User {

    private Long id;
    private String username;
    private String password;
    private Set<String> roles;

    public User() {
        this.roles = new HashSet<>();
    }

    public User(Long id, String username, String password, Set<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles != null ? new HashSet<>(roles) : new HashSet<>();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles != null ? new HashSet<>(roles) : new HashSet<>(); }
}