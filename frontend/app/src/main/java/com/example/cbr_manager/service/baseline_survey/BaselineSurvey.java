package com.example.cbr_manager.service.baseline_survey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaselineSurvey {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("date_created")
    @Expose
    private String dateCreated;

    @SerializedName("client")
    @Expose
    private Integer client;

    @SerializedName("user_creator")
    @Expose
    private Integer userCreator;

    // health
    @SerializedName("general_health")
    @Expose
    private String generalHealth;

    @SerializedName("have_access_rehab")
    @Expose
    private String haveAccessRehab;

    @SerializedName("assistive_device_have")
    @Expose
    private String assistiveDeviceHave;

    @SerializedName("assistive_device_working")
    @Expose
    private String assistiveDeviceWorking;

    @SerializedName("assistive_device_need")
    @Expose
    private String assistiveDeviceNeed;

    @SerializedName("assistive_device")
    @Expose
    private String assistiveDevice;

    @SerializedName("health_satisfaction")
    @Expose
    private String healthSatisfaction;
}
