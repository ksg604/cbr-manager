package com.example.cbr_manager.service.visit;

import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.user.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Visit {

    @SerializedName("client_id")
    @Expose
    private int clientID;
    @SerializedName("user_creator")
    @Expose
    private String userID;
    @SerializedName("additional_notes")
    @Expose
    private String additionalInfo;
    @SerializedName("client")
    @Expose
    private Client client = new Client();

    public Visit(String additionalInfo, int clientID, String userID, Client client) {
        this.additionalInfo = additionalInfo;
        this.clientID = clientID;
        this.userID = userID;
        this.client = client;
    }

    public Visit() {

    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getUserID() {
        return userID;
    }

    public String setUserID(String userID) {
        return this.userID = userID;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
