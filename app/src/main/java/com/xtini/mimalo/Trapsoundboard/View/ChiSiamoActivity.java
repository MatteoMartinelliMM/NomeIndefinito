package com.xtini.mimalo.Trapsoundboard.View;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.xtini.mimalo.Trapsoundboard.R;

import static com.xtini.mimalo.Trapsoundboard.View.StarChooserActivity.COMPANY_MAIL;

public class ChiSiamoActivity extends AppCompatActivity {
    private AdView AdBanner;
    private String AdMobAppId = "ca-app-pub-7408325265716426~9273012450";
    private String AdBannerId = "ca-app-pub-7408325265716426/2040619185";
    private TextView termini_e_condizioni, contatti;
    private ImageView img_termini;
    private Context context;
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

        context = this;

        termini_e_condizioni = findViewById(R.id.terimi_e_condizioni);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "futura-heavy-oblique.ttf");
        contatti = findViewById(R.id.contact);
        contatti.setTypeface(typeface);
        contatti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uriText = COMPANY_MAIL ;

                Uri uri = Uri.parse(uriText);

                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(uri);
                startActivity(Intent.createChooser(sendIntent, "Contattaci"));
            }
        });
        termini_e_condizioni.setTypeface(typeface);
        img_termini = findViewById(R.id.img_termini);

        img_termini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.button_clicked));
                termini_e_condizioni.startAnimation(AnimationUtils.loadAnimation(context,R.anim.button_clicked));
                onClickShowLicenceDialog();
            }
        });


    }

    public void onClickShowLicenceDialog(){
        LicenceDialog licenceDialog = new LicenceDialog();
        licenceDialog.setStyle(DialogFragment.STYLE_NORMAL,android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        licenceDialog.show(getFragmentManager(), "");
    }
}
