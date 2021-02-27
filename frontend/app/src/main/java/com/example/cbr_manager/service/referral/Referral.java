package com.example.cbr_manager.service.referral;

import com.example.cbr_manager.service.referral.ServiceDetails.ServiceDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Referral {
    private ServiceDetail serviceDetail;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("date_created")
    @Expose
    private String dateCreated;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("outcome")
    @Expose
    private String outcome;

    @SerializedName("service_type")
    @Expose
    private String serviceType;

    @SerializedName("client")
    @Expose
    private Integer client;

    @SerializedName("user_creator")
    @Expose
    private Integer userCreator;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(Integer userCreator) {
        this.userCreator = userCreator;
    }

    public void setServiceDetail(ServiceDetail serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    public ServiceDetail getServiceDetail(){
        return this.serviceDetail;
    }
}

