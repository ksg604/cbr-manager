package com.example.cbr_manager.ui.alert.alert_list;

import com.example.cbr_manager.service.alert.Alert;

public class AlertListRecyclerItem {

    private String myTitle;
    private String myBody;

    private String myDate;

    private Alert alert;

    public AlertListRecyclerItem(String title, String body, Alert alert, String date) {
        this.myTitle = title;
        this.myBody = body;
        this.alert = alert;
        this.myDate = date;
    }

    public String getmyTitle() {
        return myTitle;
    }

    public String getmyBody() {
        return myBody;
    }

    public String getmyDate() {
        return myDate;
    }

    public Alert getAlert() {
        return alert;
    }


}
