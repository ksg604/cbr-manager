package com.example.cbr_manager.service.referral.ServiceDetails;

import androidx.room.ColumnInfo;

import com.example.cbr_manager.service.referral.Referral;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

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

    public String getInfo(@NotNull String serviceType){
        switch(serviceType){
            case "Wheelchair":
                String info =
                        "Usage Experience: " + usageExperience +"\n"
                                + "Hip width: " + clientHipWidth + "\n"
                                + "Existing Wheelchair: " + clientHasExistingWheelchair.toString() +"\n";
                if(clientHasExistingWheelchair){
                    info += "Wheelchair repairable: " + isWheelChairRepairable;
                }
                return info;
            case "Physiotherapy":
                if(other_description.equals("")){other_description="None";}
                return "Condition: " + condition +"\n" + "Other Description: " + other_description;
            case "Prosthetic":
                return "Knee Injury Location: " + kneeInjuryLocation;
            case "Orthotic":
                return "Elbow Injury Location: " + elbowInjuryLocation;
            case "Other":
                if(description.equals("")){description="None";}
                return "Description: " + description;
        }

        return "";
    }

    @SerializedName("condition")
    @Expose
    private String condition;
    @SerializedName("other_description")
    @Expose
    private String other_description;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getOther_description() {
        return other_description;
    }

    public void setOther_description(String other_description) {

        this.other_description = other_description;
    }

    @SerializedName("knee_injury_location")
    @Expose
    private String kneeInjuryLocation;

    public String getKneeInjuryLocation() {
        return kneeInjuryLocation;
    }

    public void setKneeInjuryLocation(String kneeInjuryLocation) {
        this.kneeInjuryLocation = kneeInjuryLocation;
    }

    @SerializedName("description")
    @Expose
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    @SerializedName("elbow_injury_location")
    @Expose
    private String elbowInjuryLocation;

    public String getElbowInjuryLocation() {
        return elbowInjuryLocation;
    }

    public void setElbowInjuryLocation(String elbowInjuryLocation) {
        this.elbowInjuryLocation = elbowInjuryLocation;
    }

    @SerializedName("usage_experience")
    @Expose
    private String usageExperience;

    @SerializedName("is_wheel_chair_repairable")
    @Expose
    private Boolean isWheelChairRepairable;

    @SerializedName("client_has_existing_wheelchair")
    @Expose
    private Boolean clientHasExistingWheelchair;

    @SerializedName("client_hip_width")
    @Expose
    private Float clientHipWidth;

    public Boolean getWheelChairRepairable() {
        return isWheelChairRepairable;
    }

    public void setWheelChairRepairable(Boolean wheelChairRepairable) {
        isWheelChairRepairable = wheelChairRepairable;
    }

    public Boolean getClientHasExistingWheelchair() {
        return clientHasExistingWheelchair;
    }

    public void setClientHasExistingWheelchair(Boolean clientHasExistingWheelchair) {
        this.clientHasExistingWheelchair = clientHasExistingWheelchair;
    }

    public Float getClientHipWidth() {
        return clientHipWidth;
    }

    public void setClientHipWidth(Float clientHipWidth) {
        this.clientHipWidth = clientHipWidth;
    }

    public String getUsageExperience() {
        return usageExperience;
    }

    public void setUsageExperience(String usageExperience) {
        this.usageExperience = usageExperience;
    }

}
