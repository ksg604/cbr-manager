package com.example.cbr_manager.ui.homepage;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cbr_manager.R;

public class HomepageActivity extends AppCompatActivity {
    private Button newClientButton, newVisitButton, dashboardButton;
    private Button newReferralButton, clientListButton, syncButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Home Page");
        setContentView(R.layout.homepage);

        newClientButton = findViewById(R.id.newClientButton);
        setPreDrawListener(newClientButton);
        newVisitButton = findViewById(R.id.newVisitButton);
        setPreDrawListener(newVisitButton);
        dashboardButton = findViewById(R.id.dashboardButton);
        setPreDrawListener(dashboardButton);
        newReferralButton = findViewById(R.id.newReferralButton);
        setPreDrawListener(newReferralButton);
        clientListButton = findViewById(R.id.clientListButton);
        setPreDrawListener(clientListButton);
        syncButton = findViewById(R.id.syncButton);
        setPreDrawListener(newClientButton);
    }
    private void setPreDrawListener(Button button) {
        ViewTreeObserver viewTreeObserver = button.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                button.getViewTreeObserver().removeOnPreDrawListener(this);
                //scale to 90% of button height
                DroidUtils.scaleButtonDrawables(button, 0.9);
                return true;
            }
        });
    }
    public static final class DroidUtils {

        /** scale the Drawables of a button to "fit"
         *  For left and right drawables: height is scaled
         *  eg. with fitFactor 1 the image has max. the height of the button.
         *  For top and bottom drawables: width is scaled:
         *  With fitFactor 0.9 the image has max. 90% of the width of the button
         *  */
        public static void scaleButtonDrawables(Button button, double fitFactor) {
            Drawable[] drawables = button.getCompoundDrawables();

            for (int i = 0; i < drawables.length; i++) {
                if (drawables[i] != null) {
                    int imgWidth = drawables[i].getIntrinsicWidth();
                    int imgHeight = drawables[i].getIntrinsicHeight();
                    if ((imgHeight > 0) && (imgWidth > 0)) {    //might be -1
                        float scale;
                        if ((i == 0) || (i == 2)) { //left or right -> scale height
                            scale = (float) (button.getHeight() * fitFactor) / imgHeight;
                        } else { //top or bottom -> scale width
                            scale = (float) (button.getWidth() * fitFactor) / imgWidth;
                        }
                        if (scale < 1.0) {
                            Rect rect = drawables[i].getBounds();
                            int newWidth = (int)(imgWidth * scale);
                            int newHeight = (int)(imgHeight * scale);
                            rect.left = rect.left + (int)(0.5 * (imgWidth - newWidth));
                            rect.top = rect.top + (int)(0.5 * (imgHeight - newHeight));
                            rect.right = rect.left + newWidth;
                            rect.bottom = rect.top + newHeight;
                            drawables[i].setBounds(rect);
                        }
                    }
                }
            }
        }
    }
}
