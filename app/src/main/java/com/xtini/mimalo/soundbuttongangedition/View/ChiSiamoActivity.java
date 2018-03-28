package com.xtini.mimalo.soundbuttongangedition.View;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.xtini.mimalo.soundbuttongangedition.R;

public class ChiSiamoActivity extends AppCompatActivity {
    private AdView AdBanner;
    private String AdMobAppId = "ca-app-pub-7408325265716426~9273012450";
    private String AdBannerId = "ca-app-pub-7408325265716426/2040619185";

    @SuppressLint("WrongViewCast")
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

    public void showLicenzaDialog(View v){
        LicenceDialog licenceDialog = new LicenceDialog();
        licenceDialog.setStyle(DialogFragment.STYLE_NORMAL,android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        licenceDialog.show(getFragmentManager(), "");
    }
}
