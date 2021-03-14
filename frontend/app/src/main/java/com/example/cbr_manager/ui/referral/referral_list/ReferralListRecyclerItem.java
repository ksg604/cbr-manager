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

    public ReferralListRecyclerItem(String status, String type, String referTo, Referral referral, String date,int clientId) {
        this.status = status;
        this.type = type;
        this.referTo = "Refer to: "+referTo;
        this.referral = referral;
        this.date = date;
        this.clientId = clientId;
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
}
