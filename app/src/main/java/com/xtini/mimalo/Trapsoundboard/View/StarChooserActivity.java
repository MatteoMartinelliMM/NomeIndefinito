package com.xtini.mimalo.Trapsoundboard.View;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.xtini.mimalo.Trapsoundboard.Control.ShowExplainDialog;
import com.xtini.mimalo.Trapsoundboard.Control.StarChooserAdapter;
import com.xtini.mimalo.Trapsoundboard.Control.UtilitySharedPreferences;
import com.xtini.mimalo.Trapsoundboard.Model.TrapStar;
import com.xtini.mimalo.Trapsoundboard.R;

import java.util.ArrayList;
import java.util.Random;

import static com.xtini.mimalo.Trapsoundboard.View.SplashScreenActivity.FIRS_ACCESS;
import static com.xtini.mimalo.Trapsoundboard.View.SplashScreenActivity.TONY_EFFE;

// import aggiunti per video AdMob
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;

public class StarChooserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ShowExplainDialog {

    //View Elements
    private AlertDialog.Builder builder;
    private AlertDialog alert;
    private Toast toast;
    private DrawerLayout drawer;
    NavigationView navigationView;
    private RewardedVideoAd rewardedVideoAd;
    private AdView adBanner;
    //Model Elenents
    public static final String COMPANY_MAIL = "mailto:mimalogroup@gmail.com";
    public static final String SUBJECT = "?subject=";
    public static final String BODY = "&body=";
    public static final String SE_NON_METTETE_QUESTO_SUONO_SIETE_SOLO_DEGLI_STUPIDI_BUFETTI = "Se non mettete questo suono siete solo degli stupidi bufetti.";
    public static final String AGGIUNTA_SUONO = "Aggiunta suono";
    public static final String PREMI_DI_NUOVO_PER_CHIUDERE_L_APPLICAZIONE = "Premi di nuovo per chiudere l'applicazione";
    private ArrayList<TrapStar> trapStars;
    private FloatingActionButton random;
    //Variabili per caricamento videoAd
    private String AdMobAppId = "ca-app-pub-7408325265716426~9273012450";
    private String AdMobPubId = "ca-app-pub-7408325265716426/7975763724";
    //Controllr Elements
    private boolean doubleBackToExitPressedOnce = false;
    private int index;
    private HorizontalInfiniteCycleViewPager pager;
    private Context context;
    private ShowExplainDialog showExplainDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_chooser);

        Toolbar toolbar = setActionBar();

        context = this;

        showExplainDialog = this;
        trapStars = SplashScreenActivity.trapStars;
        setTheCarusel(TONY_EFFE);
        random = findViewById(R.id.random);
        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int i = r.nextInt(30);
                pager.setCurrentItem(i % trapStars.size(),false);
            }
        });
        setDrawerMenu(toolbar);

        adBanner = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adBanner.loadAd(adRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MobileAds.initialize(this, AdMobAppId);
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                Log.d("An ad has Loaded", "AdMob");
            }

            @Override
            public void onRewardedVideoAdOpened() {
                Log.d("An ad has Opened", "AdMob");
            }

            @Override
            public void onRewardedVideoStarted() {
                Log.d("An ad has Started", "AdMob");
            }

            @Override
            public void onRewardedVideoAdClosed() {
                Log.d("An ad has Closed", "AdMob");
                rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
                rewardedVideoAd.loadAd(AdMobAppId, new AdRequest.Builder().build());
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                rewardedVideoAd.setImmersiveMode(false);
                String clickedArtist = UtilitySharedPreferences.getClickedArtistName(context);
                UtilitySharedPreferences.lockOrUnlockArtist(context, clickedArtist, true);
                reloadTheCarusel(UtilitySharedPreferences.getClickedArtistName(context));

                Toast toast = ViewUtilities.createCustomToast(context, drawer, "Hai appena sbloccato un artista , grazie del supporto ! ");
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();

                rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
                rewardedVideoAd.loadAd(AdMobPubId, new AdRequest.Builder().build());
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                Log.d("Video Left Application", "AdMob");
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Log.d("An ad has FailedToLoad", "AdMob");
            }

            @Override
            public void onRewardedVideoCompleted() {
                Log.d("An ad has Loaded", "AdMob");

            }
        });
        //for test
        rewardedVideoAd.loadAd(AdMobPubId, new AdRequest.Builder().build());
    }


    private void setDrawerMenu(Toolbar toolbar) {
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private Toolbar setActionBar() {

        setTitle("Trap Soundboard");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void setTheCarusel(String trapStarName) {
        index = getArtistIndex(trapStarName);
        pager = findViewById(R.id.horizontal_cycle);
        StarChooserAdapter adapter = new StarChooserAdapter(trapStars, getBaseContext(), this, showExplainDialog);
        pager.setAdapter(adapter);
        pager.setCurrentItem(index, false);
    }


    public void reloadTheCarusel(String trapStarName) {
        setTheCarusel(trapStarName);
    }

    private int getArtistIndex(String artistName) { //RECUPERA INDICE DI QUALE ARTISTA METTERE IN PRIMO PIANO
        int index = 0;
        for (int i = 0; i < trapStars.size(); i++)
            if (trapStars.get(i).getTrapStarName().equals(artistName)) {
                index = i;
                break;
            }
        return index;
    }

    // onBackPressed + relative method called in
    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            doubleTapForCloseApp();
        }
    }


    private void doubleTapForCloseApp() {
        if (doubleBackToExitPressedOnce) {

            killTheAppByApiChooice();

            return;
        }

        this.doubleBackToExitPressedOnce = true;
        toast = ViewUtilities.createCustomToast(this, drawer, PREMI_DI_NUOVO_PER_CHIUDERE_L_APPLICAZIONE);
        toast.show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void killTheAppByApiChooice() {
        if (Build.VERSION.SDK_INT >= 21) {
            finishAndRemoveTask();
        } else {
            finish();
        }

    }


    // navigationMenu + relative method called in
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_suggerisci:
                adviceSound();
                break;
            case R.id.nav_condividi_app:
                shareApp();
                break;
            case R.id.nav_rate_us:
                rateUs();
                break;
            case R.id.nav_chi_siamo:
                startActivity(new Intent(this, ChiSiamoActivity.class));
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void rateUs() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            this.startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " Sorry, Not able to open!", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareApp() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String sAux = "\nSe non la scarichi sei solo uno stupido BUFU!\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            navigationView.getMenu().getItem(0).setChecked(false);
            startActivityForResult(Intent.createChooser(i, "Condividi CIUCCIONE!"), 0);
        } catch (Exception e) {
            //e.toString();
        }
    }

    public void adviceSound() {
        String uriText =
                COMPANY_MAIL +
                        SUBJECT + Uri.encode(AGGIUNTA_SUONO) +
                        BODY + Uri.encode(SE_NON_METTETE_QUESTO_SUONO_SIETE_SOLO_DEGLI_STUPIDI_BUFETTI);

        Uri uri = Uri.parse(uriText);

        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(uri);
        startActivity(Intent.createChooser(sendIntent, "Suggeriscici un suono"));
    }

    @Override
    public void showExplainDialog() {
        //creo un alert dialog con due opzioni ok cancel
        builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme)
                .setMessage("Vuoi sbloccare questo artista guardando un video pubblicità ?").setPositiveButton("Seeh", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (rewardedVideoAd.isLoaded()) {
                            rewardedVideoAd.setImmersiveMode(true);
                            rewardedVideoAd.show();
                            //rewardedVideoAd.destroy(context);
                            //MobileAds.initialize(this, testId);
                        } else {
                            Toast toast = ViewUtilities.createCustomToast(context, drawer, "Problemi di caricamento con il video, riprova più tardi");
                            toast.show();
                            MobileAds.initialize(context, AdMobAppId);
                            rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
                            rewardedVideoAd.loadAd(AdMobPubId, new AdRequest.Builder().build());
                        }
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        alert = builder.create();
        alert.show();

    }
}
