package com.example.cbr_manager.ui.referral.referral_list;

import com.example.cbr_manager.service.referral.Referral;

public class ReferralListRecyclerItem {

    private String mStatus;
    private String mType;
    private String mDate;
    private Referral referral;

    public ReferralListRecyclerItem(String status, String type, Referral referral, String date) {
        this.mStatus = status;
        this.mType = type;
        this.referral = referral;
        this.mDate = date;
    }

    public String getmStatus() {
        return mStatus;
    }

    public String getmType() {
        return mType;
    }

    public String getmDate() {
        return mDate;
    }

    public Referral getReferral() {
        return referral;
    }


}
