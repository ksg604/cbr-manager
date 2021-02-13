package com.example.cbr_manager.service.user;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("username")
    private String username = null;
    @SerializedName("password")
    private String password = null;
    @SerializedName("email")
    private String email = null;
    @SerializedName("first_name")
    private String first_name = null;
    @SerializedName("last_name")
    private String last_name = null;

    public User(String username, String password, String email, String first_name, String last_name) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
