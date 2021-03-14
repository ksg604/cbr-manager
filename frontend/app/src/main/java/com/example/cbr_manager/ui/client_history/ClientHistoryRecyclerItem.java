package com.example.cbr_manager.ui.client_history;

import com.example.cbr_manager.service.alert.Alert;

public class ClientHistoryRecyclerItem {

    private String myValue;
    private String myDate;

    public ClientHistoryRecyclerItem(String value,String date) {
        this.myValue = value;
        this.myDate = date;
    }

    public String getmyValue() {
        return myValue;
    }

    public String getmyDate() {
        return myDate;
    }
}
