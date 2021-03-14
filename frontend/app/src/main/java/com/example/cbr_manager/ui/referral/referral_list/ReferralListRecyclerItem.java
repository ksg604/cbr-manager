package com.example.cbr_manager.ui.referral.referral_list;

import com.example.cbr_manager.service.referral.Referral;

public class ReferralListRecyclerItem {

    private String myStatus;
    private String myType;
    private String myDate;
    private String myReferTo;
    private Referral referral;

    public ReferralListRecyclerItem(String status, String type, String referTo, Referral referral, String date) {
        this.myStatus = status;
        this.myType = type;
        this.myReferTo = "Refer to: "+referTo;
        this.referral = referral;
        this.myDate = date;
    }

    public String getmyStatus() {
        return myStatus;
    }

    public String getmyType() {
        return myType;
    }

    public String getmyDate() {
        return myDate;
    }

    public String getmyReferTo() {return myReferTo;}

    public Referral getReferral() {
        return referral;
    }

}
