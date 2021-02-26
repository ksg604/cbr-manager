package com.example.cbr_manager.service.referral.ServiceDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WheelchairServiceDetail extends ServiceDetail {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("usage_experience")
    @Expose
    private String usageExperience;
    @SerializedName("is_wheel_chair_repairable")
    @Expose
    private Boolean isWheelChairRepairable;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsageExperience() {
        return usageExperience;
    }

    public void setUsageExperience(String usageExperience) {
        this.usageExperience = usageExperience;
    }

    public Boolean getIsWheelChairRepairable() {
        return isWheelChairRepairable;
    }

    public void setIsWheelChairRepairable(Boolean isWheelChairRepairable) {
        this.isWheelChairRepairable = isWheelChairRepairable;
    }
}
