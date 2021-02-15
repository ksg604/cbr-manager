package com.example.cbr_manager.service.visit;

import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.user.User;
import com.google.gson.annotations.SerializedName;

public class Visit {

    @SerializedName("additional_info")
    private String additionalInfo;

    private Client client;

    private User user;
}
