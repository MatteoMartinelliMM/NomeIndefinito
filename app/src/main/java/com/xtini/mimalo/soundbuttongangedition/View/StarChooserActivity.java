package com.xtini.mimalo.soundbuttongangedition.View;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import android.widget.Button;
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

public class StarChooserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ShowExplainDialog {


    public static final String N_ALTRA_VOLTA = "N'altra volta";
    public static final String COMPANY_MAIL = "mailto:mimalogroup@gmail.com";
    public static final String SUBJECT = "?subject=";
    public static final String BODY = "&body=";
    public static final String SE_NON_METTETE_QUESTO_SUONO_SIETE_SOLO_DEGLI_STUPIDI_BUFETTI = "Se non mettete questo suono siete solo degli stupidi bufetti.";
    public static final String AGGIUNTA_SUONO = "Aggiunta suono";
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

    //Variabili per caricamento videoAd
    private RewardedVideoAd rewardedVideoAd;
    private String AdMobAppId = "ca-app-pub-7408325265716426~9273012450";
    private String AdMobPubId = "ca-app-pub-7408325265716426/7975763724";
    private String adMobPubTest = "ca-app-pub-3940256099942544/6300978111";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_chooser);

        Toolbar toolbar = setActionBar();
       /* Button button = findViewById(R.id.pigrizia);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilitySharedPreferences.clearAll(view.getContext());
                finish();
            }
        });*/
        context = this;
        Intent intent = getIntent();
        isFirstAccess = intent.getBooleanExtra(FIRS_ACCESS, false);
        showExplainDialog = this;
        trapStars = SplashScreenActivity.trapStars;
        setTheCarusel(TONY_EFFE);
        setDrawerMenu(toolbar);

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

            killTheAppByApiChooice();

            return;
        }

        this.doubleBackToExitPressedOnce = true;
        toast = ViewUtilities.createCustomToast(this, drawer, N_ALTRA_VOLTA);
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
        Uri uri = Uri.parse("market://details?id=" + "com.facebook.katana"/*getPackageName()*/); //TODO: scommentare getPackageName()
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
