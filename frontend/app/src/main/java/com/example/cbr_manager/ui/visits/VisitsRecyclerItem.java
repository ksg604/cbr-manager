package com.example.cbr_manager.ui.visits;
import com.example.cbr_manager.service.visit.Visit;

public class VisitsRecyclerItem {
    private int myImageResource;
    private String myText1;
    private String myText2;

    private Visit visit;

    public VisitsRecyclerItem(int myImageResource, String myText1, String myText2, Visit visit) {
        this.myImageResource = myImageResource;
        this.myText1 = myText1;
        this.myText2 = myText2;
        this.visit = visit;
    }

    public int getId() {
        return visit.getId();
    }

    public int getmyImageResource() {
        return myImageResource;
    }

    public String getmyText1() {
        return myText1;
    }

    public String getmyText2() {
        return myText2;
    }

    public Visit getVisit() {
        return visit;
    }
}
