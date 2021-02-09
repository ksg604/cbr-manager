package com.example.cbr_manager.ui.createvisit;

public class HealthProvision {
    private String providedHealth;
    private String associatedTextBox;
    private VisitSurvey.Goal goalMetHealthProvision;
    private String conclusionOutcomeHealthProvision;


    public HealthProvision(String providedHealth, String associatedTextBox) {
        this.providedHealth = providedHealth;
        this.associatedTextBox = associatedTextBox;
    }

    public String getProvidedHealth() {
        return providedHealth;
    }

    public VisitSurvey.Goal getGoalMetHealthProvision() {
        return goalMetHealthProvision;
    }

    public void setGoalMetHealthProvision(VisitSurvey.Goal goalMetHealthProvision) {
        this.goalMetHealthProvision = goalMetHealthProvision;
    }

    public String getConclusionOutcomeHealthProvision() {
        return conclusionOutcomeHealthProvision;
    }

    public void setConclusionOutcomeHealthProvision(String conclusionOutcomeHealthProvision) {
        this.conclusionOutcomeHealthProvision = conclusionOutcomeHealthProvision;
    }

    public void setProvidedHealth(String providedHealth) {
        this.providedHealth = providedHealth;
    }

    public String getAssociatedTextBox() {
        return associatedTextBox;
    }

    public void setAssociatedTextBox(String associatedTextBox) {
        this.associatedTextBox = associatedTextBox;
    }
}
