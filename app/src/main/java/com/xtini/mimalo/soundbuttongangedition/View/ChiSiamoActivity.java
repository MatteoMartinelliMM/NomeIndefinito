package com.xtini.mimalo.soundbuttongangedition.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.xtini.mimalo.soundbuttongangedition.R;

public class ChiSiamoActivity extends AppCompatActivity {
    private AdView AdBanner;

    private String AdMobAppId = "ca-app-pub-7408325265716426~9273012450";
    private String AdBannerId = "ca-app-pub-7408325265716426/2040619185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_siamo);

        MobileAds.initialize(this, AdMobAppId);
        //Inizializzazione Banner in View
        AdBanner = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdBanner.loadAd(adRequest);

    }
}
