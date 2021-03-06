package com.example.cbr_manager.service.visit;

import com.example.cbr_manager.service.client.Client;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Visit {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("client_id")
    @Expose
    private int clientId;
    @SerializedName("user_creator")
    @Expose
    private int userId;
    @SerializedName("additional_notes")
    @Expose
    private String additionalInfo;
    @SerializedName("client")
    @Expose
    private Client client = new Client();
    @SerializedName("datetime_created")
    @Expose
    private Timestamp datetimeCreated;

    public Visit(String additionalInfo, int clientId, int userId, Client client) {
        this.additionalInfo = additionalInfo;
        this.clientId = clientId;
        this.userId = userId;
        this.client = client;
    }

    public Visit() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
