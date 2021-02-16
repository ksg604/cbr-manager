package com.example.cbr_manager.service;

import android.util.Log;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.auth.AuthService;
import com.example.cbr_manager.service.auth.AuthToken;
import com.example.cbr_manager.service.auth.LoginUserPass;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.client.ClientService;
import com.example.cbr_manager.service.user.User;
import com.example.cbr_manager.service.user.UserService;
import com.example.cbr_manager.ui.clientlist.ClientListRecyclerItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class APIService {

    private static APIService INSTANCE = null;
    public AuthService authService;
    public ClientService clientService;
    public UserService userService;
    public String username;
    public int userid;
    public String email;

    private APIService() {
    }

    public static APIService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new APIService();
        }
        return (INSTANCE);
    }

    public void authenticate(LoginUserPass loginUserPass, Callback<AuthToken> listener) {
        authService = new AuthService(loginUserPass);
        authService.fetchAuthToken().enqueue(new Callback<AuthToken>() {
            @Override
            public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {
                if (response.isSuccessful()) {
                    AuthToken token = response.body();
                    authService.setAuthToken(token);
                    initializeServices(token);
                }
                listener.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<AuthToken> call, Throwable t) {
                // Todo: not sure proper method of error handling

                listener.onFailure(call, t);
            }
        });
    }

    public void storeUserInfo(){
        userService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> userList = response.body();
                    for (User user : userList) {
                        if (username.equals(user.getUsername())){
                            email = user.getEmail();
                            userid = user.getId();
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

    public int getUserid(){
        return userid;
    }

    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }

    public void initializeServices(AuthToken token) {
        this.clientService = new ClientService(token);
        this.userService = new UserService(token);
    }

    public boolean isAuthenticated() {
        // Todo needs a better check, maybe a specific endpoint to check validity of auth token
        return authService.getAuthToken() != null;
    }

    public void testInitializeUserService() {
        this.userService = new UserService();
    }
}
