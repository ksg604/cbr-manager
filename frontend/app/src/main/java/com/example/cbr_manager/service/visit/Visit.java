package com.example.cbr_manager.service.visit;

import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.client.ClientService;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Visit {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("client_id")
    @Expose
    private int clientID;
    @SerializedName("user_creator")
    @Expose
    private int userID;
    @SerializedName("additional_notes")
    @Expose
    private String additionalInfo;
    @SerializedName("client")
    @Expose
    private Client client = new Client();
    @SerializedName("datetime_created")
    @Expose
    private Timestamp datetimeCreated;

    public Visit(String additionalInfo, int clientID, int userID, Client client) {
        this.additionalInfo = additionalInfo;
        this.clientID = clientID;
        this.userID = userID;
        this.client = client;
    }

    public Visit() {

    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
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

    public int getUserID() {
        return userID;
    }

    public int setUserID(int userID) {
        return this.userID = userID;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Timestamp getDatetimeCreated() { return this.datetimeCreated; }

    public void setDatetimeCreated(Timestamp datetimeCreated) {
        this.datetimeCreated = datetimeCreated;
    }

}
