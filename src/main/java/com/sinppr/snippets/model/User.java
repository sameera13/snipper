package com.sinppr.snippets.model;

public class User {
    private int id;
    private String email;
    private String password;
    private String passwordHash;

    public User() {}

    public User(int id, String email, String password, String passwordHash) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.passwordHash = passwordHash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }
    public String getPasswordHash() {
        return passwordHash;
    }


    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
