package com.example.cbr_manager.ui.referral.referral_list;

import com.example.cbr_manager.service.referral.Referral;

public class ReferralListRecyclerItem {

    private String status;
    private String type;
    private String date;
    private String referTo;
    private Referral referral;

    public ReferralListRecyclerItem(String status, String type, String referTo, Referral referral, String date) {
        this.status = status;
        this.type = type;
        this.referTo = "Refer to: "+referTo;
        this.referral = referral;
        this.date = date;
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

}
