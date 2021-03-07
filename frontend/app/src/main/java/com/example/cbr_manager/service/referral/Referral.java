package com.example.cbr_manager.service.referral;

import com.example.cbr_manager.service.referral.ServiceDetails.ServiceDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Referral {
    @SerializedName("service_detail")
    @Expose
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

    public Referral() {
    }

    public Referral(ServiceDetail serviceDetail, String dateCreated, String status, String outcome, String serviceType, Integer client, Integer userCreator, String refer_to, String photoURL) {
        this.serviceDetail = serviceDetail;
        this.dateCreated = dateCreated;
        this.status = status;
        this.outcome = outcome;
        this.serviceType = serviceType;
        this.client = client;
        this.userCreator = userCreator;
        this.refer_to = refer_to;
        this.photoURL = photoURL;
    }

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

    @SerializedName("refer_to")
    @Expose
    private String refer_to;

    @SerializedName("photo")
    @Expose(serialize = false) // Do not send 'photo' attribute to API
    private String photoURL;

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getRefer_to() {
        return refer_to;
    }

    public void setRefer_to(String refer_to) {
        this.refer_to = refer_to;
    }

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

