package com.example.cbr_manager.ui.create_client;

import android.widget.TextView;

public class InvalidCreateClientFormException extends Exception {
    TextView view;

    InvalidCreateClientFormException(String s, TextView problemView){
        super(s);
        view = problemView;
    }
}
