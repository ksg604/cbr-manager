package com.example.cbr_manager.ui.alert.alert_list;

import com.example.cbr_manager.service.alert.Alert;

public class AlertListRecyclerItem {

    private String title;
    private String body;

    private String date;

    private Alert alert;

    public AlertListRecyclerItem(String title, String body, Alert alert, String date) {
        this.title = title;
        this.body = body;
        this.alert = alert;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public Alert getAlert() {
        return alert;
    }


}
