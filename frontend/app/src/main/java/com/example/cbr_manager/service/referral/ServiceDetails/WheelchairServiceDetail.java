package com.example.cbr_manager.service.referral.ServiceDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WheelchairServiceDetail extends ServiceDetail {
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

    public Boolean getIsWheelChairRepairable() {
        return isWheelChairRepairable;
    }

    public void setIsWheelChairRepairable(Boolean isWheelChairRepairable) {
        this.isWheelChairRepairable = isWheelChairRepairable;
    }
}
