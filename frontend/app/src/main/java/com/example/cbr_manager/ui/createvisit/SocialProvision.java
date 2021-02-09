package com.example.cbr_manager.ui.createvisit;

public class SocialProvision {
    private String providedSocial;
    private String associatedTextBox;
    private VisitSurvey.Goal goalMetSocialProvision;
    private String conclusionOutComeSocialProvision;

    public SocialProvision(String providedSocial, String associatedTextBox) {
        this.providedSocial = providedSocial;
        this.associatedTextBox = associatedTextBox;
    }

    public VisitSurvey.Goal getGoalMetSocialProvision() {
        return goalMetSocialProvision;
    }

    public void setGoalMetSocialProvision(VisitSurvey.Goal goalMetSocialProvision) {
        this.goalMetSocialProvision = goalMetSocialProvision;
    }

    public String getConclusionOutComeSocialProvision() {
        return conclusionOutComeSocialProvision;
    }

    public void setConclusionOutComeSocialProvision(String conclusionOutComeSocialProvision) {
        this.conclusionOutComeSocialProvision = conclusionOutComeSocialProvision;
    }

    public String getProvidedSocial() {
        return providedSocial;
    }

    public void setProvidedSocial(String providedSocial) {
        this.providedSocial = providedSocial;
    }

    public String getAssociatedTextBox() {
        return associatedTextBox;
    }

    public void setAssociatedTextBox(String associatedTextBox) {
        this.associatedTextBox = associatedTextBox;
    }
}
