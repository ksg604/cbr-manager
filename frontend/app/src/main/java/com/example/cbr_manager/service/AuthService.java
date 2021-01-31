package com.example.cbr_manager.service;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthService {
    /*
    This class authenticates the user
     */

    private static final String BASE_URL = "http://127.0.0.1:8000/api/";
    private final String username;
    private final String password;
    private final OkHttpClient httpClient;
    private String authToken;

    public AuthService(String username, String password) {
        this.httpClient = new OkHttpClient();
        this.username = username;
        this.password = password;
    }

    public void authenticate() throws IOException {
        this.authToken = getTokenAuth();
    }

    public String getTokenAuth() throws IOException {
        String url = BASE_URL + "token-auth/";
        OkHttpClient client = new OkHttpClient();

        RequestBody body = getAuthRequestBody();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);

//        Response response = call.execute();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("oof");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println("woo");
            }
        });
        return "";
    }

    private RequestBody getAuthRequestBody() {
        return new FormBody.Builder()
                .add("username", this.username)
                .add("password", this.password)
                .build();
    }
}
