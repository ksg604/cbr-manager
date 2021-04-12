package com.example.cbr_manager.service.referral;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.cbr_manager.service.referral.ServiceDetails.ServiceDetail;
import com.example.cbr_manager.utils.CBRTimestamp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity (tableName = "referral")
public class Referral extends CBRTimestamp {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    @SerializedName("id")
    @Expose
    private Integer serverId;

    @Embedded
    @SerializedName("service_detail")
    @Expose
    private ServiceDetail serviceDetail;


    @SerializedName("status")
    @Expose
    private String status = "CREATED";


    public Referral() {
        super();
        ServiceDetail physiotherapyServiceDetail = new ServiceDetail();
        physiotherapyServiceDetail.setCondition("Amputee");
        this.serviceDetail = physiotherapyServiceDetail;
        this.status = "CREATED";
        this.outcome = "";
        this.serviceType = "Physiotherapy";
        this.clientId = 0;
        this.fullName = "";
        this.userId = 0;
        this.refer_to = "";
        this.photoURL = "images/default.png";
    }
    @Ignore
    public Referral(ServiceDetail serviceDetail, String status, String outcome, String serviceType, Integer clientId, String fullName, Integer userId, String refer_to, String photoURL) {
        super();
        this.serviceDetail = serviceDetail;
        this.status = status;
        this.outcome = outcome;
        this.serviceType = serviceType;
        this.clientId = clientId;
        this.fullName = fullName;
        this.userId = userId;
        this.refer_to = refer_to;
        this.photoURL = photoURL;
    }

    @SerializedName("outcome")
    @Expose
    private String outcome;

    @SerializedName("service_type")
    @Expose
    private String serviceType;

    @SerializedName("client_id")
    @Expose
    private Integer clientId;

    @SerializedName("client_name")
    @Expose
    private String fullName;

    @SerializedName("user_creator")
    @Expose
    private Integer userId;

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setServiceDetail(ServiceDetail serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    public ServiceDetail getServiceDetail(){
        return this.serviceDetail;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

}

