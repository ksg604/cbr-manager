package com.example.cbr_manager.ui.referral.referral_list;

import com.example.cbr_manager.service.referral.Referral;

public class ReferralListRecyclerItem {

    private String mStatus;
    private String mType;
    private String mDate;
    private String mReferTo;
    private Referral referral;

    public ReferralListRecyclerItem(String status, String type, String referTo, Referral referral, String date) {
        this.mStatus = status;
        this.mType = type;
        this.mReferTo = "Refer to: "+referTo;
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

    public String getmReferTo() {return mReferTo;}

    public Referral getReferral() {
        return referral;
    }

}
