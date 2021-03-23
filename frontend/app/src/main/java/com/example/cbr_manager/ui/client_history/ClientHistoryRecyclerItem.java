package com.example.cbr_manager.ui.client_history;

import com.example.cbr_manager.service.alert.Alert;

public class ClientHistoryRecyclerItem {

    private String value;
    private String date;

    public ClientHistoryRecyclerItem(String value,String date) {
        this.value = value;
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }
}
