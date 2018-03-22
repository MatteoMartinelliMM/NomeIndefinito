package com.xtini.mimalo.soundbuttongangedition.View;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.xtini.mimalo.soundbuttongangedition.Control.ShowExplainDialog;
import com.xtini.mimalo.soundbuttongangedition.Control.StarChooserAdapter;
import com.xtini.mimalo.soundbuttongangedition.Control.UtilitySharedPreferences;
import com.xtini.mimalo.soundbuttongangedition.Model.TrapStar;
import com.xtini.mimalo.soundbuttongangedition.R;

import java.util.ArrayList;

import static com.xtini.mimalo.soundbuttongangedition.View.SplashScreenActivity.FIRS_ACCESS;
import static com.xtini.mimalo.soundbuttongangedition.View.SplashScreenActivity.TONY_EFFE;

// import aggiunti per video AdMob
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;

//aggiungo interfaccia RewardedVideoAdListener per reward
public class StarChooserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ShowExplainDialog, RewardedVideoAdListener {


    NavigationView navigationView;
    private boolean doubleBackToExitPressedOnce = false;
    private Toast toast;
    private DrawerLayout drawer;
    private ArrayList<TrapStar> trapStars;
    public static boolean isFirstAccess;
    private int index;
    private HorizontalInfiniteCycleViewPager pager;
    private Context context;
    private AlertDialog.Builder builder;
    private AlertDialog alert;
    private ShowExplainDialog showExplainDialog;

    //Variabile per caricamento videoAd
    //TODO VANNO USATI AdMobPubId e AppId , quelli usati ora sono per test
    private RewardedVideoAd rewardedVideoAd;
    private String AdMobAppId = "ca-app-pub-7408325265716426~9273012450";
    private String AdMobPubId = "ca-app-pub-7408325265716426/7975763724";
    String testPub = "ca-app-pub-3940256099942544/5224354917";

    String testId = "ca-app-pub-3940256099942544~3347511713";
    // onCreate + relative Method called in
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_chooser);

        Toolbar toolbar = setActionBar();

        context = this;
        Intent intent = getIntent();
        isFirstAccess = intent.getBooleanExtra(FIRS_ACCESS, false);
        showExplainDialog = this;
        trapStars = SplashScreenActivity.trapStars;
        setTheCarusel(TONY_EFFE);
        setDrawerMenu(toolbar);

        //ca-app-pub-7408325265716426~9273012450

        //for test
        //MobileAds.initialize(this, AdMobAppId);
        MobileAds.initialize(this, testId);

        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        //for test

        //rewardedVideoAd.loadAd(AdMobPubId, new AdRequest.Builder().build());
        rewardedVideoAd.loadAd(testPub, new AdRequest.Builder().build());
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
        pager.setCurrentItem(index, true);
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
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        toast = ViewUtilities.createCustomToast(this, drawer, "N'altra volta");
        toast.show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
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
        Uri uri = Uri.parse("market://details?id=" + "com.facebook.katana"/*getPackageName()*/);
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
            sAux = sAux + "https://play.google.com/store/apps/details?id=Orion.Soft \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            navigationView.getMenu().getItem(0).setChecked(false);
            startActivityForResult(Intent.createChooser(i, "Condividi CIUCCIONE!"), 0);
        } catch (Exception e) {
            //e.toString();
        }
    }

    public void adviceSound() {
        String uriText =
                "mailto:mimalo@gmail.com" +
                        "?subject=" + Uri.encode("Aggiunta suono") +
                        "&body=" + Uri.encode("Se non mettete questo suono siete solo degli stupidi bufetti.");

        Uri uri = Uri.parse(uriText);

        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(uri);
        startActivity(Intent.createChooser(sendIntent, "Suggeriscici un suono"));
    }

    @Override
    public void showExplainDialog() {
        //creo un alert dialog con due opzioni ok cancel
        builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme)
                .setMessage("Vuoi sbloccare questo artista guardando un video pubblicità ?").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (rewardedVideoAd.isLoaded()) {
                            rewardedVideoAd.show();
                            rewardedVideoAd.loadAd(testPub,new AdRequest.Builder().build());
                        }
                    }
                }).setNegativeButton("Cancella", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        alert = builder.create();
        alert.show();

    }

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
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

        Log.d("Reward received " + rewardItem.getType(), "AdMob");
        Toast.makeText(this, "Hai appena sbloccato un artista , grazie del supporto ! Clicca ancora per sbloccarlo", Toast.LENGTH_LONG).show();

        String clickedArtist = UtilitySharedPreferences.getClickedArtistName(this);
         //Sblocco l'artista che ho cliccato dopo il reward ( DOVREI AGGIORNARE ORA LA CAROSEL )
        UtilitySharedPreferences.lockOrUnlockArtist(this, clickedArtist, true);
        //TODO dovrei aggiornare la carosel ora per evitare un altro click dell'utente

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
}
