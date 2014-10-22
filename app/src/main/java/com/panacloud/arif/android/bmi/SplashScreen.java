package com.panacloud.arif.android.bmi;

/**
 * Created by Arif on 10/16/2014.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.panacloud.arif.android.R;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getActionBar().setSubtitle(R.string.version);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/danielbd.ttf");

        ((TextView)findViewById(R.id.textView1)).setTypeface(tf);
        ((TextView)findViewById(R.id.textView2)).setTypeface(tf);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, BMIActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
