package com.shubhamk1500.qrreader;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;


public class AboutDeveloper extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_about_developer);
        setTitle("APP DEVELOPER");
        StartAppSDK.init(this, "206000786", true);
        StartAppAd.disableSplash();

    }

    @Override
    public void onBackPressed() {
        Intent nextActivity = new Intent(this, MainActivity.class);

        startActivity(nextActivity);
        StartAppAd.showAd(this);
        finish();
        super.onBackPressed();
    }
}
