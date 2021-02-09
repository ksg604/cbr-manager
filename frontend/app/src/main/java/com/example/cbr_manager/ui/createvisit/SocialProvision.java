package com.example.cbr_manager.ui.createvisit;

public class SocialProvision {
    private String providedSocial;
    private String associatedTextBox;

    public SocialProvision(String providedSocial, String associatedTextBox) {
        this.providedSocial = providedSocial;
        this.associatedTextBox = associatedTextBox;
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
