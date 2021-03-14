package com.example.cbr_manager.service.sync;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cbr_manager.utils.CBRTimestamp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "status")
public class Status extends CBRTimestamp {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("client_last_updated")
    @Expose
    private String clientLastUpdated;

    public Status(Integer id, String createdAt, String updatedAt, String clientLastUpdated) {
        this.setCreatedAt(createdAt);
        this.setUpdatedAt(updatedAt);
        this.id = id;
        this.clientLastUpdated = clientLastUpdated;
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", clientLastUpdated='" + clientLastUpdated + '\'' +
                '}';
    }

    public static Status createDefaultInstance() {
        return new Status(
                1,
                "",
                "",
                ""
        );
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientLastUpdated() {
        return clientLastUpdated;
    }

    public void setClientLastUpdated(String clientLastUpdated) {
        this.clientLastUpdated = clientLastUpdated;
    }
}
