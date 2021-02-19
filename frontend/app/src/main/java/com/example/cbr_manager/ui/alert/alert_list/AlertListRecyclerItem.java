package com.example.cbr_manager.ui.alert.alert_list;

import com.example.cbr_manager.service.alert.Alert;

public class AlertListRecyclerItem {

    private String mTitle;
    private String mBody;

    private String mDate;

    private Alert alert;

    public AlertListRecyclerItem(String title, String body, Alert alert, String date) {
        this.mTitle = title;
        this.mBody = body;
        this.alert = alert;
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

    public Alert getAlert() {
        return alert;
    }


}
