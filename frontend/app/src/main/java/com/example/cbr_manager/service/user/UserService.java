package com.example.cbr_manager.service.user;

import com.example.cbr_manager.service.BaseService;

import java.util.List;

import retrofit2.Call;

public class UserService extends BaseService {

    private UserAPI userAPI;

    public UserService(String authToken) {
        super(authToken, UserAPI.class);
        this.userAPI = this.buildRetrofitAPI();
    }

    public Call<List<User>> getUsers() {
        return this.userAPI.getUsers(authHeader);
    }

    public Call<User> createUser(User user) {
        // note: user id for the user object can be anything. default it manually to -1.
        return this.userAPI.createUser(authHeader, user);
    }

    public Call<User> getUser(int userId) {
        return this.userAPI.getUser(authHeader, userId);
    }

    public Call<User> getCurrentUser() {
        return this.userAPI.getCurrentUser(authHeader);
    }

}