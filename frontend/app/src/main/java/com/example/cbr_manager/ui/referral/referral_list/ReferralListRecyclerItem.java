package com.example.cbr_manager.ui.referral.referral_list;

import android.util.Log;

import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.referral.Referral;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferralListRecyclerItem {

    private final String status;
    private final String type;
    private final String date;
    private final String referTo;
    private final Referral referral;
    private final int clientId;
    private String clientName;

    public ReferralListRecyclerItem(String status, String type, String referTo, Referral referral, String date,int clientId, String clientName) {
        this.status = status;
        this.type = type;
        this.referTo = referTo;
        this.referral = referral;
        this.date = date;
        this.clientId = clientId;
        this.clientName = clientName;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getReferTo() {return referTo;}

    public Referral getReferral() {
        return referral;
    }

    public int getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }
}
