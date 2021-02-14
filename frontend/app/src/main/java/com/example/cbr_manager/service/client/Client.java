package com.example.cbr_manager.service.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// Todo: figure out image upload
@JsonIgnoreProperties({"photo"})
public class Client {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("risk_score")
    private Integer riskScore;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("location")
    private String location;
    @JsonProperty("gps_location")
    private String gpsLocation;
    @JsonProperty("consent")
    private String consent;
    @JsonProperty("village_no")
    private Integer villageNo;
    @JsonProperty("date")
    private String date;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("age")
    private Integer age;
    @JsonProperty("contact_client")
    private Integer contactClient;
    @JsonProperty("care_present")
    private String carePresent;
    @JsonProperty("contact_care")
    private Integer contactCare;
    @JsonProperty("photo")
    private String photo;
    @JsonProperty("disability")
    private String disability;
    @JsonProperty("health_risk")
    private Integer healthRisk;
    @JsonProperty("health_require")
    private String healthRequire;
    @JsonProperty("health_goal")
    private String healthGoal;
    @JsonProperty("education_risk")
    private Integer educationRisk;
    @JsonProperty("education_require")
    private String educationRequire;
    @JsonProperty("education_goal")
    private String educationGoal;
    @JsonProperty("social_risk")
    private Integer socialRisk;
    @JsonProperty("social_require")
    private String socialRequire;
    @JsonProperty("social_goal")
    private String socialGoal;

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("risk_score")
    public Integer getRiskScore() {
        return riskScore;
    }

    @JsonProperty("risk_score")
    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("gps_location")
    public String getGpsLocation() {
        return gpsLocation;
    }

    @JsonProperty("gps_location")
    public void setGpsLocation(String gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    @JsonProperty("consent")
    public String getConsent() {
        return consent;
    }

    @JsonProperty("consent")
    public void setConsent(String consent) {
        this.consent = consent;
    }

    @JsonProperty("village_no")
    public Integer getVillageNo() {
        return villageNo;
    }

    @JsonProperty("village_no")
    public void setVillageNo(Integer villageNo) {
        this.villageNo = villageNo;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("age")
    public Integer getAge() {
        return age;
    }

    @JsonProperty("age")
    public void setAge(Integer age) {
        this.age = age;
    }

    @JsonProperty("contact_client")
    public Integer getContactClient() {
        return contactClient;
    }

    @JsonProperty("contact_client")
    public void setContactClient(Integer contactClient) {
        this.contactClient = contactClient;
    }

    @JsonProperty("care_present")
    public String getCarePresent() {
        return carePresent;
    }

    @JsonProperty("care_present")
    public void setCarePresent(String carePresent) {
        this.carePresent = carePresent;
    }

    @JsonProperty("contact_care")
    public Integer getContactCare() {
        return contactCare;
    }

    @JsonProperty("contact_care")
    public void setContactCare(Integer contactCare) {
        this.contactCare = contactCare;
    }

    @JsonProperty("photo")
    public String getPhoto() {
        return photo;
    }

    @JsonProperty("photo")
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @JsonProperty("disability")
    public String getDisability() {
        return disability;
    }

    @JsonProperty("disability")
    public void setDisability(String disability) {
        this.disability = disability;
    }

    @JsonProperty("health_risk")
    public Integer getHealthRisk() {
        return healthRisk;
    }

    @JsonProperty("health_risk")
    public void setHealthRisk(Integer healthRisk) {
        this.healthRisk = healthRisk;
    }

    @JsonProperty("health_require")
    public String getHealthRequire() {
        return healthRequire;
    }

    @JsonProperty("health_require")
    public void setHealthRequire(String healthRequire) {
        this.healthRequire = healthRequire;
    }

    @JsonProperty("health_goal")
    public String getHealthGoal() {
        return healthGoal;
    }

    @JsonProperty("health_goal")
    public void setHealthGoal(String healthGoal) {
        this.healthGoal = healthGoal;
    }

    @JsonProperty("education_risk")
    public Integer getEducationRisk() {
        return educationRisk;
    }

    @JsonProperty("education_risk")
    public void setEducationRisk(Integer educationRisk) {
        this.educationRisk = educationRisk;
    }

    @JsonProperty("education_require")
    public String getEducationRequire() {
        return educationRequire;
    }

    @JsonProperty("education_require")
    public void setEducationRequire(String educationRequire) {
        this.educationRequire = educationRequire;
    }

    @JsonProperty("education_goal")
    public String getEducationGoal() {
        return educationGoal;
    }

    @JsonProperty("education_goal")
    public void setEducationGoal(String educationGoal) {
        this.educationGoal = educationGoal;
    }

    @JsonProperty("social_risk")
    public Integer getSocialRisk() {
        return socialRisk;
    }

    @JsonProperty("social_risk")
    public void setSocialRisk(Integer socialRisk) {
        this.socialRisk = socialRisk;
    }

    @JsonProperty("social_require")
    public String getSocialRequire() {
        return socialRequire;
    }

    @JsonProperty("social_require")
    public void setSocialRequire(String socialRequire) {
        this.socialRequire = socialRequire;
    }

    @JsonProperty("social_goal")
    public String getSocialGoal() {
        return socialGoal;
    }

    @JsonProperty("social_goal")
    public void setSocialGoal(String socialGoal) {
        this.socialGoal = socialGoal;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
