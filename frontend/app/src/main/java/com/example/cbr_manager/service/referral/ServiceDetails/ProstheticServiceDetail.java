package com.example.cbr_manager.service.referral.ServiceDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProstheticServiceDetail extends ServiceDetail{
    public String getKneeInjuryLocation() {
        return kneeInjuryLocation;
    }

    public void setKneeInjuryLocation(String kneeInjuryLocation) {
        this.kneeInjuryLocation = kneeInjuryLocation;
    }

    @SerializedName("knee_injury_location")
    @Expose
    private String kneeInjuryLocation;

    public String getInfo(){
        return "Knee Injury Location: " + kneeInjuryLocation;
    }
}
