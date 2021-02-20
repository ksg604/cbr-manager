package com.example.cbr_manager.service.alert;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.w3c.dom.CDATASection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Alert {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("id")
    @Expose
    private int id;

    public Alert(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void formatDate() {
        String datePython = getDate().substring(0,19);
        String patternOutput = "MM/dd/yyyy   HH:mm";
        String patternInput = "yyyy-MM-DD'T'HH:mm:ss";

        SimpleDateFormat sdfInput = new SimpleDateFormat(patternInput);
        SimpleDateFormat sdfOutput = new SimpleDateFormat(patternOutput);
        Date date = null;
        try {
            date = sdfInput.parse(datePython);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setDate(sdfOutput.format(date));
    }
}