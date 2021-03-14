package com.example.cbr_manager.ui.visits;
import com.example.cbr_manager.service.visit.Visit;

public class VisitsRecyclerItem {
    private int imageResource;
    private String titleText;
    private String bodyText;

    private Visit visit;

    public VisitsRecyclerItem(int imageResource, String titleText, String bodyText, Visit visit) {
        this.imageResource = imageResource;
        this.titleText = titleText;
        this.bodyText = bodyText;
        this.visit = visit;
    }

    public int getId() {
        return visit.getId();
    }

    public int getImageResource() {
        return imageResource;
    }

    public String gettitleText() {
        return titleText;
    }

    public String getbodyText() {
        return bodyText;
    }

    public Visit getVisit() {
        return visit;
    }
}
