package com.example.cbr_manager.ui.visits;
import com.example.cbr_manager.service.visit.Visit;

public class VisitsRecyclerItem {
    private String dateString;
    private String bodyText;
    private String purposeText;
    private String locationText;

    private Visit visit;

    public VisitsRecyclerItem(String titleText, String bodyText, Visit visit, String purposeText, String locationText) {
        this.dateString = titleText;
        this.bodyText = bodyText;
        this.visit = visit;
        this.purposeText = purposeText;
        this.locationText = locationText;
    }

    public int getId() {
        return visit.getId();
    }

    public String getDateString() {
        return dateString;
    }

    public String getBodyText() {
        return bodyText;
    }

    public Visit getVisit() {
        return visit;
    }

    public String getPurposeText() {
        return purposeText;
    }

    public String getLocationText() {
        return locationText;
    }
}
