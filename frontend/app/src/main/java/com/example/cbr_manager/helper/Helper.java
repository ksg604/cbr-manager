package com.example.cbr_manager.helper;

import com.example.cbr_manager.BuildConfig;
import com.example.cbr_manager.service.auth.AuthToken;


public class Helper {
    public static String getAPIUrl() {
        return BuildConfig.API_URL;
    }

    public static String formatTokenHeader(AuthToken authToken){
        return "Token " + authToken.token;
    }
}
