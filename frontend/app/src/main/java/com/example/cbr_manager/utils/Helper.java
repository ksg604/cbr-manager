package com.example.cbr_manager.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cbr_manager.BuildConfig;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class Helper {
    public static String getAPIUrl() {
        return BuildConfig.API_URL;
    }

    public static String formatTokenHeader(String token) {
        return "Token " + token;
    }

    public static Drawable getImageFromUrl(String url) throws IOException {
        URL urlObj = new URL(url);
        InputStream stream = (InputStream) urlObj.getContent();
        return Drawable.createFromStream(stream, url);
    }

    public static void setImageViewFromURL(String url, ImageView view, int defaultDrawableResId){
        Picasso picasso = Picasso.get();
        picasso.load(url).into(view);
        if (view.getDrawable() == null){
            view.setImageResource(defaultDrawableResId);
        }
    }

    public static String riskToColourCode(int riskScore){
        if (riskScore >= 10) {
            return "#b02323";
        } else if (riskScore < 10 && riskScore >= 5) {
            return "#c45404";
        } else {
            return "#c49704";
        }
    }

    public static Boolean validateTextViewNotNull(TextView editTextView) {
        if (editTextView.getText().toString().trim().isEmpty()) {
            return false;
        }
        return true;

    }
}
