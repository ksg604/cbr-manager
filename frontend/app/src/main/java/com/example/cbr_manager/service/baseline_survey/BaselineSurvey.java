package com.example.cbr_manager.service.baseline_survey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaselineSurvey {

    public BaselineSurvey() {

    }

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

    // education
    @SerializedName("attend_school")
    @Expose
    private String attendSchool;

    @SerializedName("grade")
    @Expose
    private Integer grade;

    @SerializedName("reason_no_school")
    @Expose
    private String reasonNoSchool;

    @SerializedName("been_to_school")
    @Expose
    private String beenToSchool;

    @SerializedName("want_to_go_school")
    @Expose
    private String wantToGoSchool;

    // social
    @SerializedName("feel_valued")
    @Expose
    private String feelValued;

    @SerializedName("feel_independent")
    @Expose
    private String feelIndependent;

    @SerializedName("able_to_participate")
    @Expose
    private String ableToParticipate;

    @SerializedName("disability_affects_social")
    @Expose
    private String disabilityAffectsSocial;

    @SerializedName("discriminated")
    @Expose
    private String discriminated;

    // livelihood
    @SerializedName("working")
    @Expose
    private String working;

    @SerializedName("job")
    @Expose
    private String job;

    @SerializedName("employment")
    @Expose
    private String employment;

    @SerializedName("meets_financial")
    @Expose
    private String meetsFinancial;

    @SerializedName("disability_affects_work")
    @Expose
    private String disabilityAffectsWork;

    @SerializedName("want_work")
    @Expose
    private String wantWork;

    // food, nutrition
    @SerializedName("food_security")
    @Expose
    private String foodSecurity;

    @SerializedName("enough_food")
    @Expose
    private String enoughFood;

    @SerializedName("child_nourishment")
    @Expose
    private String childNourishment;

    // empowerment
    @SerializedName("member_of_organizations")
    @Expose
    private String memberOfOrganizations;

    @SerializedName("aware_of_rights")
    @Expose
    private String awareOfRights;

    @SerializedName("able_to_influence")
    @Expose
    private String ableToInfluence;

    // shelter
    @SerializedName("adequate_shelter")
    @Expose
    private String adequateShelter;

    @SerializedName("access_essentials")
    @Expose
    private String accessEssentials;


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

    public String getGeneralHealth() {
        return generalHealth;
    }

    public void setGeneralHealth(String generalHealth) {
        this.generalHealth = generalHealth;
    }

    public String getHaveAccessRehab() {
        return haveAccessRehab;
    }

    public void setHaveAccessRehab(String haveAccessRehab) {
        this.haveAccessRehab = haveAccessRehab;
    }

    public String getAssistiveDeviceHave() {
        return assistiveDeviceHave;
    }

    public void setAssistiveDeviceHave(String assistiveDeviceHave) {
        this.assistiveDeviceHave = assistiveDeviceHave;
    }

    public String getAssistiveDeviceWorking() {
        return assistiveDeviceWorking;
    }

    public void setAssistiveDeviceWorking(String assistiveDeviceWorking) {
        this.assistiveDeviceWorking = assistiveDeviceWorking;
    }

    public String getAssistiveDeviceNeed() {
        return assistiveDeviceNeed;
    }

    public void setAssistiveDeviceNeed(String assistiveDeviceNeed) {
        this.assistiveDeviceNeed = assistiveDeviceNeed;
    }

    public String getAssistiveDevice() {
        return assistiveDevice;
    }

    public void setAssistiveDevice(String assistiveDevice) {
        this.assistiveDevice = assistiveDevice;
    }

    public String getHealthSatisfaction() {
        return healthSatisfaction;
    }

    public void setHealthSatisfaction(String healthSatisfaction) {
        this.healthSatisfaction = healthSatisfaction;
    }

    public String getAttendSchool() {
        return attendSchool;
    }

    public void setAttendSchool(String attendSchool) {
        this.attendSchool = attendSchool;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getReasonNoSchool() {
        return reasonNoSchool;
    }

    public void setReasonNoSchool(String reasonNoSchool) {
        this.reasonNoSchool = reasonNoSchool;
    }

    public String getBeenToSchool() {
        return beenToSchool;
    }

    public void setBeenToSchool(String beenToSchool) {
        this.beenToSchool = beenToSchool;
    }

    public String getWantToGoSchool() {
        return wantToGoSchool;
    }

    public void setWantToGoSchool(String wantToGoSchool) {
        this.wantToGoSchool = wantToGoSchool;
    }

    public String getFeelValued() {
        return feelValued;
    }

    public void setFeelValued(String feelValued) {
        this.feelValued = feelValued;
    }

    public String getFeelIndependent() {
        return feelIndependent;
    }

    public void setFeelIndependent(String feelIndependent) {
        this.feelIndependent = feelIndependent;
    }

    public String getAbleToParticipate() {
        return ableToParticipate;
    }

    public void setAbleToParticipate(String ableToParticipate) {
        this.ableToParticipate = ableToParticipate;
    }

    public String getDisabilityAffectsSocial() {
        return disabilityAffectsSocial;
    }

    public void setDisabilityAffectsSocial(String disabilityAffectsSocial) {
        this.disabilityAffectsSocial = disabilityAffectsSocial;
    }

    public String getDiscriminated() {
        return discriminated;
    }

    public void setDiscriminated(String discriminated) {
        this.discriminated = discriminated;
    }

    public String getWorking() {
        return working;
    }

    public void setWorking(String working) {
        this.working = working;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public String getMeetsFinancial() {
        return meetsFinancial;
    }

    public void setMeetsFinancial(String meetsFinancial) {
        this.meetsFinancial = meetsFinancial;
    }

    public String getDisabilityAffectsWork() {
        return disabilityAffectsWork;
    }

    public void setDisabilityAffectsWork(String disabilityAffectsWork) {
        this.disabilityAffectsWork = disabilityAffectsWork;
    }

    public String getWantWork() {
        return wantWork;
    }

    public void setWantWork(String wantWork) {
        this.wantWork = wantWork;
    }

    public String getFoodSecurity() {
        return foodSecurity;
    }

    public void setFoodSecurity(String foodSecurity) {
        this.foodSecurity = foodSecurity;
    }

    public String getEnoughFood() {
        return enoughFood;
    }

    public void setEnoughFood(String enoughFood) {
        this.enoughFood = enoughFood;
    }

    public String getChildNourishment() {
        return childNourishment;
    }

    public void setChildNourishment(String childNourishment) {
        this.childNourishment = childNourishment;
    }

    public String getMemberOfOrganizations() {
        return memberOfOrganizations;
    }

    public void setMemberOfOrganizations(String memberOfOrganizations) {
        this.memberOfOrganizations = memberOfOrganizations;
    }

    public String getAwareOfRights() {
        return awareOfRights;
    }

    public void setAwareOfRights(String awareOfRights) {
        this.awareOfRights = awareOfRights;
    }

    public String getAbleToInfluence() {
        return ableToInfluence;
    }

    public void setAbleToInfluence(String ableToInfluence) {
        this.ableToInfluence = ableToInfluence;
    }

    public String getAdequateShelter() {
        return adequateShelter;
    }

    public void setAdequateShelter(String adequateShelter) {
        this.adequateShelter = adequateShelter;
    }

    public String getAccessEssentials() {
        return accessEssentials;
    }

    public void setAccessEssentials(String accessEssentials) {
        this.accessEssentials = accessEssentials;
    }
}
