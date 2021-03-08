package com.example.cbr_manager.service.referral.ServiceDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrthoticServiceDetail extends ServiceDetail {
    @SerializedName("elbow_injury_location")
    @Expose
    private String elbowInjuryLocation;

    public String getElbowInjuryLocation() {
        return elbowInjuryLocation;
    }

    public void setElbowInjuryLocation(String elbowInjuryLocation) {
        this.elbowInjuryLocation = elbowInjuryLocation;
    }

    public String getInfo(){
        return "Elbow Injury Location: " + elbowInjuryLocation;
    }
}
