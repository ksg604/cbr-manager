package com.example.cbr_manager.service.client;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// Todo: figure out image upload
public class Client {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("risk_score")
    @Expose
    private Integer riskScore;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("gps_location")
    @Expose
    private String gpsLocation;
    @SerializedName("consent")
    @Expose
    private String consent;
    @SerializedName("village_no")
    @Expose
    private Integer villageNo;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("contact_client")
    @Expose
    private Integer contactClient;
    @SerializedName("care_present")
    @Expose
    private String carePresent;
    @SerializedName("contact_care")
    @Expose
    private Integer contactCare;
    @SerializedName("photo")
    @Expose(serialize = false) // Do not send 'photo' attribute to API
    private String photoURL;
    @SerializedName("disability")
    @Expose
    private String disability;
    @SerializedName("health_risk")
    @Expose
    private Integer healthRisk;
    @SerializedName("health_require")
    @Expose
    private String healthRequire;
    @SerializedName("health_goal")
    @Expose
    private String healthGoal;
    @SerializedName("education_risk")
    @Expose
    private Integer educationRisk;
    @SerializedName("education_require")
    @Expose
    private String educationRequire;
    @SerializedName("education_goal")
    @Expose
    private String educationGoal;
    @SerializedName("social_risk")
    @Expose
    private Integer socialRisk;
    @SerializedName("social_require")
    @Expose
    private String socialRequire;
    @SerializedName("social_goal")
    @Expose
    private String socialGoal;

    @JsonProperty("is_cbr_purpose")
    private boolean isCBRPurpose;

    @JsonProperty("is_disability_referral_purpose")
    private boolean isDisabilityReferralPurpose;

    @JsonProperty("is_disability_follow_up_purpose")
    private boolean isDisabilityFollowUpPurpose;

    @JsonProperty("is_health_provision")
    private boolean isHealthProvision;

    @JsonProperty("is_education_provision")
    private boolean isEducationProvision;

    @JsonProperty("is_social_provision")
    private boolean isSocialProvision;

    @JsonProperty("cbr_worker_name")
    private String cbrWorkerName;

    @JsonProperty("location_visit_gps")
    private String locationVisitGPS;

    @JsonProperty("location_drop_down")
    private String locationDropDown;

    @JsonProperty("village_no_visit")
    private Integer villageNoVisit;

    @JsonProperty("wheelchair_health_provision")
    private boolean wheelchairHealthProvision;

    @JsonProperty("prosthetic_health_provision")
    private boolean prostheticHealthProvision;

    @JsonProperty("orthotic_health_provision")
    private boolean orthoticHealthProvision;

    @JsonProperty("repairs_health_provision")
    private boolean repairsHealthProvision;

    @JsonProperty("referral_health_provision")
    private boolean referralHealthProvision;

    @JsonProperty("advice_health_provision")
    private boolean adviceHealthProvision;

    @JsonProperty("advocacy_health_provision")
    private boolean advocacyHealthProvision;

    @JsonProperty("encouragement_health_provision")
    private boolean encouragementHealthProvision;

    @SerializedName("wheelchair_health_provision_text")
    @Expose
    private String wheelchairHealthProvisionText;

    @SerializedName("prosthetic_health_provision_text")
    @Expose
    private String prostheticHealthProvisionText;

    @SerializedName("orthotic_health_provision_text")
    @Expose
    private String orthoticHealthProvisionText;

    @SerializedName("repairs_health_provision_text")
    @Expose
    private String repairsHealthProvisionText;

    @SerializedName("referral_health_provision_text")
    @Expose
    private String referralHealthProvisionText;

    @SerializedName("advice_health_provision_text")
    @Expose
    private String adviceHealthProvisionText;

    @SerializedName("advocacy_health_provision_text")
    @Expose
    private String advocacyHealthProvisionText;

    @SerializedName("encouragement_health_provision_text")
    @Expose
    private String encouragementHealthProvisionText;

    @SerializedName("goal_met_health_provision")
    @Expose
    private String goalMetHealthProvision;

    @SerializedName("conclusion_health_provision")
    @Expose
    private String conclusionHealthProvision;

    @SerializedName("advice_education_provision")
    @Expose
    private boolean adviceEducationProvision;

    @SerializedName("advocacy_education_provision")
    @Expose
    private boolean advocacyEducationProvision;

    @SerializedName("referral_education_provision")
    @Expose
    private boolean referralEducationProvision;

    @SerializedName("encouragement_education_provision")
    @Expose
    private boolean encouragementEducationProvision;

    @SerializedName("advice_education_provision_text")
    @Expose
    private String adviceEducationProvisionText;

    @SerializedName("advocacy_education_provision_text")
    @Expose
    private String advocacyEducationProvisionText;

    @SerializedName("referral_education_provision_text")
    @Expose
    private String referralEducationProvisionText;

    @SerializedName("encouragement_education_provision_text")
    @Expose
    private String encouragementEducationProvisionText;

    @SerializedName("goal_met_education_provision")
    @Expose
    private String goalMetEducationProvision;

    @SerializedName("conclusion_education_provision")
    @Expose
    private String conclusionEducationProvision;

    @SerializedName("advice_social_provision")
    @Expose
    private boolean adviceSocialProvision;

    @SerializedName("advocacy_social_provision")
    @Expose
    private boolean advocacySocialProvision;

    @SerializedName("referral_social_provision")
    @Expose
    private boolean referralSocialProvision;

    @SerializedName("encouragement_social_provision")
    @Expose
    private boolean encouragementSocialProvision;

    @SerializedName("advice_social_provision_text")
    @Expose
    private String adviceSocialProvisionText;

    @SerializedName("advocacy_social_provision_text")
    @Expose
    private String advocacySocialProvisionText;

    @SerializedName("referral_social_provision_text")
    @Expose
    private String referralSocialProvisionText;

    @SerializedName("encouragement_social_provision_text")
    @Expose
    private String encouragementSocialProvisionText;

    @SerializedName("goal_met_social_provision")
    @Expose
    private String goalMetSocialProvision;

    @SerializedName("conclusion_social_provision")
    @Expose
    private String conclusionSocialProvision;

    public Client() {
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(String gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public String getConsent() {
        return consent;
    }

    public void setConsent(String consent) {
        this.consent = consent;
    }

    public Integer getVillageNo() {
        return villageNo;
    }

    public void setVillageNo(Integer villageNo) {
        this.villageNo = villageNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getContactClient() {
        return contactClient;
    }

    public void setContactClient(Integer contactClient) {
        this.contactClient = contactClient;
    }

    public String getCarePresent() {
        return carePresent;
    }

    public void setCarePresent(String carePresent) {
        this.carePresent = carePresent;
    }

    public Integer getContactCare() {
        return contactCare;
    }

    public void setContactCare(Integer contactCare) {
        this.contactCare = contactCare;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public Integer getHealthRisk() {
        return healthRisk;
    }

    public void setHealthRisk(Integer healthRisk) {
        this.healthRisk = healthRisk;
    }

    public String getHealthRequire() {
        return healthRequire;
    }

    public void setHealthRequire(String healthRequire) {
        this.healthRequire = healthRequire;
    }

    public String getHealthGoal() {
        return healthGoal;
    }

    public void setHealthGoal(String healthGoal) {
        this.healthGoal = healthGoal;
    }

    public Integer getEducationRisk() {
        return educationRisk;
    }

    public void setEducationRisk(Integer educationRisk) {
        this.educationRisk = educationRisk;
    }

    public String getEducationRequire() {
        return educationRequire;
    }

    public void setEducationRequire(String educationRequire) {
        this.educationRequire = educationRequire;
    }

    public String getEducationGoal() {
        return educationGoal;
    }

    public void setEducationGoal(String educationGoal) {
        this.educationGoal = educationGoal;
    }

    public Integer getSocialRisk() {
        return socialRisk;
    }

    public void setSocialRisk(Integer socialRisk) {
        this.socialRisk = socialRisk;
    }

    public String getSocialRequire() {
        return socialRequire;
    }

    public void setSocialRequire(String socialRequire) {
        this.socialRequire = socialRequire;
    }

    public String getSocialGoal() {
        return socialGoal;
    }

    public void setSocialGoal(String socialGoal) {
        this.socialGoal = socialGoal;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
