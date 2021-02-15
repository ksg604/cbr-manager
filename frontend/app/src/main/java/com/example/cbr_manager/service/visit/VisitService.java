package com.example.cbr_manager.service.visit;

import com.example.cbr_manager.BuildConfig;
import com.example.cbr_manager.helper.Helper;
import com.example.cbr_manager.service.auth.AuthToken;

public class VisitService {
    private static final String BASE_URL = BuildConfig.API_URL;

    private final AuthToken authToken;

    private final String authHeader;

    public VisitService(AuthToken authToken) {
        this.authToken = authToken;
        this.authHeader = Helper.formatTokenHeader(this.authToken);
    }
}
