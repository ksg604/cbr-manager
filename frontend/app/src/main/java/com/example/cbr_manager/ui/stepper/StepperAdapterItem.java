package com.example.cbr_manager.ui.stepper;

import androidx.fragment.app.Fragment;

public class StepperAdapterItem {
    Fragment fragment;
    String fragmentTitle;

    public StepperAdapterItem(Fragment fragment, String fragmentTitle) {
        this.fragment = fragment;
        this.fragmentTitle = fragmentTitle;
    }
}
