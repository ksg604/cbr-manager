package com.example.cbr_manager.service.referral.ServiceDetails;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceDetail {
    @SerializedName("type")
    @Expose
    private String type;

    @ColumnInfo(name = "serviceId")
    @SerializedName("id")
    @Expose
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo(){
        return "";
    }
}
