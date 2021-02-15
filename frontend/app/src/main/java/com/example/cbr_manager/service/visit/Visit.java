package com.example.cbr_manager.service.visit;

import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.user.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Visit {

    @SerializedName("additional_info")
    @Expose
    private String additionalInfo;

    @SerializedName("client_id")
    @Expose
    private int clientID;

    @SerializedName("user_id")
    @Expose
    private int userID;

    @SerializedName("client")
    @Expose
    private Client client;

}
