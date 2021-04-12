package com.example.cbr_manager.service.alert;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Update;

import com.example.cbr_manager.utils.CBRTimestamp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.w3c.dom.CDATASection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "alert")
public class Alert extends CBRTimestamp {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    @SerializedName("id")
    @Expose
    private Integer serverId;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("body")
    @Expose
    private String body;

    @ColumnInfo(name = "markedRead")
    private Boolean markedRead;

    public Alert() {
        this.title = "";
        this.body = "";
        if(this.markedRead==null) {
            this.markedRead = false;
        }
    }

    @Ignore
    public Alert(String title, String body) {
        this.title = title;
        this.body = body;
        if(this.markedRead==null) {
            this.markedRead = false;
        }
    }

    public Boolean getMarkedRead() {
        return markedRead;
    }

    public void setMarkedRead(Boolean markedRead) {
        this.markedRead = markedRead;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}