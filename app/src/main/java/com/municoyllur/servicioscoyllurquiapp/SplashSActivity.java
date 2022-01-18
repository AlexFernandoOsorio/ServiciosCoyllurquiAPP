package com.municoyllur.servicioscoyllurquiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashSActivity extends AppCompatActivity {

    //Variable que determina el tiempo de carga inicial
    private static final long SPLASH_SCREEN_DELAY = 3000;


    private static final int MY_PERMISSIONS_REQUEST_READ_CAMERA = 0 ;
    private static final int MY_PERMISSIONS_REQUEST_READ_LOCATION = 0 ;
    private static final int MY_PERMISSIONS_REQUEST_READ_CALL = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_s);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // Start the next activity
                Intent mainIntent = new Intent().setClass(
                        SplashSActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
            }
        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);

    }
}