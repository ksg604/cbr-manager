package com.example.cbr_manager.service.referral.ServiceDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhysiotherapyServiceDetail extends ServiceDetail {
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
        if(other_description.equals("")){other_description="None";}
        this.other_description = other_description;
    }
    public String getInfo(){
        return "Condition: " + condition +"\n"
                + "Other Description: " + other_description;
    }
}
