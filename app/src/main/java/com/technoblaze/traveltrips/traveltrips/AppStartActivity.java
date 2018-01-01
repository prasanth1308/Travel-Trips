package com.technoblaze.traveltrips.traveltrips;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AppStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        // Get the shared preferences
        SharedPreferences preferences =  getSharedPreferences("my_preferences", MODE_PRIVATE);

        // Check if onboarding_complete is false
        if(!preferences.getBoolean("onboarding_complete",false)) {
            // Start the onboarding Activity
            Intent onboarding = new Intent(this, OnBoarding.class);
            startActivity(onboarding);

            // Close the main Activity
            finish();
            Toast.makeText(this, "HIT", Toast.LENGTH_SHORT).show();
            return;
        } else {
            // Start the login Activity
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);

            // Close the main Activity
            finish();
            Toast.makeText(this, "HIT", Toast.LENGTH_SHORT).show();
            //return;

        }
        checkForUpdates();
        //setContentView(R.layout.activity_app_start);
    }


    @Override
    public void onResume() {
        super.onResume();
        // ... your own onResume implementation
        checkForCrashes();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterManagers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterManagers();
    }

    private void checkForCrashes() {
        CrashManager.register(this);
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }

    private void unregisterManagers() {
        UpdateManager.unregister();
    }

}
