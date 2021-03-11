package com.example.cbr_manager.ui.create_client;

import android.widget.TextView;

import static com.example.cbr_manager.utils.Helper.validateTextViewNotNull;

public class ValidatorHelper {

    public static void validateStepperTextViewNotNull(TextView textView, String errorMessage) throws InvalidCreateClientFormException {
        if (!validateTextViewNotNull(textView)) {
            throw new InvalidCreateClientFormException(errorMessage, textView);
        }
    }
}
