package com.example.cbr_manager.service.client;

import com.google.gson.annotations.SerializedName;

public class Client {
    @SerializedName("id")
    private final int id;

    @SerializedName("risk_score")
    private int riskScore;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("location")
    private String location;

    @SerializedName("gps_location")
    private String gpsLocation;

    @SerializedName("consent")
    private String consent;

    @SerializedName("village_no")
    private int villageNo;

    @SerializedName("date")
    private String date;

    @SerializedName("gender")
    private String gender;

    @SerializedName("age")
    private int age;

    @SerializedName("contact_client")
    private int contactClient;

    @SerializedName("care_present")
    private String carePresent;

    @SerializedName("contact_care")
    private int contactCare;

    @SerializedName("photo")
    private String photo;

    @SerializedName("disability")
    private String disability;

    @SerializedName("health_risk")
    private int healthRisk;

    @SerializedName("health_require")
    private String healthRequire;

    @SerializedName("health_goal")
    private String healthGoal;

    @SerializedName("education_risk")
    private int educationRisk;

    @SerializedName("education_require")
    private String educationRequire;

    @SerializedName("education_goal")
    private String educationGoal;

    @SerializedName("social_risk")
    private int socialRisk;

    @SerializedName("social_require")
    private String socialRequire;

    @SerializedName("social_goal")
    private String socialGoal;

    public Client(String firstName, String lastName, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return this.getFirstName() + " " + this.getLastName();
    }

    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
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

    public int getVillageNo() {
        return villageNo;
    }

    public void setVillageNo(int villageNo) {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getContactClient() {
        return contactClient;
    }

    public void setContactClient(int contactClient) {
        this.contactClient = contactClient;
    }

    public String getCarePresent() {
        return carePresent;
    }

    public void setCarePresent(String carePresent) {
        this.carePresent = carePresent;
    }

    public int getContactCare() {
        return contactCare;
    }

    public void setContactCare(int contactCare) {
        this.contactCare = contactCare;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public int getHealthRisk() {
        return healthRisk;
    }

    public void setHealthRisk(int healthRisk) {
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

    public int getEducationRisk() {
        return educationRisk;
    }

    public void setEducationRisk(int educationRisk) {
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

    public int getSocialRisk() {
        return socialRisk;
    }

    public void setSocialRisk(int socialRisk) {
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
}
