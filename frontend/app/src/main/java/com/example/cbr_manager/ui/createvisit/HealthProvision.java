package com.example.cbr_manager.ui.createvisit;

public class HealthProvision {
    private String providedHealth;
    private String associatedTextBox;

    public HealthProvision(String providedHealth, String associatedTextBox) {
        this.providedHealth = providedHealth;
        this.associatedTextBox = associatedTextBox;
    }

    public String getProvidedHealth() {
        return providedHealth;
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
