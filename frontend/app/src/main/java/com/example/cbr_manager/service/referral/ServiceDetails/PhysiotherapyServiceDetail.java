package com.example.cbr_manager.service.referral.ServiceDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhysiotherapyServiceDetail extends ServiceDetail {
    @SerializedName("conditions")
    @Expose
    private String conditions;
    @SerializedName("specified_condition")
    @Expose
    private String specifiedCondition;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getSpecifiedCondition() {
        return specifiedCondition;
    }

    public void setSpecifiedCondition(String specifiedCondition) {
        this.specifiedCondition = specifiedCondition;
    }
}
