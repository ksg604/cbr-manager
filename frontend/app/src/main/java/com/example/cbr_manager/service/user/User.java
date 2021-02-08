package com.example.cbr_manager.service.user;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String passWord;

    private final int id;

    public User(String firstName, String passWord, int id) {
        this.firstName = firstName;
        this.passWord = passWord;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getId() {
        return id;
    }
}
