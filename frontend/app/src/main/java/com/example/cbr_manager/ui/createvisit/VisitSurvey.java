package com.example.cbr_manager.ui.createvisit;

import java.util.ArrayList;
import java.util.Date;

public class VisitSurvey {

    enum Goal {
        CANCELLED,
        ONGOING,
        CONCLUDED
    }

    enum DDL {
        BIDIBIDI_ZONE_1,
        BIDIBIDI_ZONE_2,
        BIDIBIDI_ZONE_3,
        BIDIBIDI_ZONE_4,
        BIDIBIDI_ZONE_5,
        PALORINYA_BASECAMP,
        PALORINYA_ZONE_1,
        PALORINYA_ZONE_2,
        PALORINYA_ZONE_3
    }

    private String surveyClientName;
    private String purposeOfVisit;
    private String ifCBR;
    private Date date;
    private String cbrWorkerName;
    private String locationOfVisit;
    private DDL locationDDL;
    private int villageNumber;
    private ArrayList<HealthProvision> healthProvisions = new ArrayList<>();
    private ArrayList<SocialProvision> socialProvision = new ArrayList<>();

    public VisitSurvey(String surveyClientName, String cbrWorkerName) {
        this.surveyClientName = surveyClientName;
        this.cbrWorkerName = cbrWorkerName;
    }

    public String getSurveyClientName() {
        return surveyClientName;
    }

    public void setSurveyClientName(String surveyClientName) {
        this.surveyClientName = surveyClientName;
    }

    public String getPurposeOfVisit() {
        return purposeOfVisit;
    }

    public void setPurposeOfVisit(String purposeOfVisit) {
        this.purposeOfVisit = purposeOfVisit;
    }

    public String getIfCBR() {
        return ifCBR;
    }

    public void setIfCBR(String ifCBR) {
        this.ifCBR = ifCBR;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCbrWorkerName() {
        return cbrWorkerName;
    }

    public void setCbrWorkerName(String cbrWorkerName) {
        this.cbrWorkerName = cbrWorkerName;
    }

    public String getLocationOfVisit() {
        return locationOfVisit;
    }

    public void setLocationOfVisit(String locationOfVisit) {
        this.locationOfVisit = locationOfVisit;
    }

    public DDL getLocationDDL() {
        return locationDDL;
    }

    public void setLocationDDL(DDL locationDDL) {
        this.locationDDL = locationDDL;
    }

    public int getVillageNumber() {
        return villageNumber;
    }

    public void setVillageNumber(int villageNumber) {
        this.villageNumber = villageNumber;
    }

    public ArrayList<HealthProvision> getHealthProvisions() {
        return healthProvisions;
    }

    public void setHealthProvisions(ArrayList<HealthProvision> healthProvisions) {
        this.healthProvisions = healthProvisions;
    }

    public ArrayList<SocialProvision> getSocialProvision() {
        return socialProvision;
    }

    public void setSocialProvision(ArrayList<SocialProvision> socialProvision) {
        this.socialProvision = socialProvision;
    }
}
