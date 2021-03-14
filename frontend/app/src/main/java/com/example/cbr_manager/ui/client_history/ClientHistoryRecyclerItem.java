package com.example.cbr_manager.ui.client_history;

import com.example.cbr_manager.service.alert.Alert;

public class ClientHistoryRecyclerItem {

    private String mValue;
    private String mDate;

    public ClientHistoryRecyclerItem(String value,String date) {
        this.mValue = value;
        this.mDate = date;
    }

    public String getmValue() {
        return mValue;
    }

    public String getmDate() {
        return mDate;
    }
}
