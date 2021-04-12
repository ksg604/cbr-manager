package com.example.cbr_manager.ui.alert.alert_list;

import com.example.cbr_manager.service.alert.Alert;

public class AlertListRecyclerItem {

    private String title;
    private String body;

    private String date;

    private Alert alert;

    private Boolean markedRead;

    public AlertListRecyclerItem(String title, String body, Alert alert, String date,Boolean markedRead) {
        this.title = title;
        this.body = body;
        this.alert = alert;
        this.date = date;
        this.markedRead = markedRead;
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

    public Boolean getMarkedRead() {
        return markedRead;
    }

    public Alert getAlert() {
        return alert;
    }


}
