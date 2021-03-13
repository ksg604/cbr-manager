package com.example.cbr_manager.utils;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

abstract public class CBRTimestamp {
    @SerializedName("updated_at")
    @Expose
    String updatedAt;

    @SerializedName("created_at")
    @Expose
    String createdAt;

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
