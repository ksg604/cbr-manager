package com.example.cbr_manager.ui.referral.referral_list;

import com.example.cbr_manager.service.referral.Referral;

public class ReferralListRecyclerItem {

    private String mTitle;
    private String mBody;

    private String mDate;

    private Referral referral;

    public ReferralListRecyclerItem(String title, String body, Referral referral, String date) {
        this.mTitle = title;
        this.mBody = body;
        this.referral = referral;
        this.mDate = date;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmBody() {
        return mBody;
    }

    public String getmDate() {
        return mDate;
    }

    public Referral getReferral() {
        return referral;
    }


}
