package com.xtini.mimalo.soundbuttongangedition.View;

import android.content.ActivityNotFoundException;
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
import android.view.MenuItem;
import android.widget.Toast;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.xtini.mimalo.soundbuttongangedition.Control.StarChooserAdapter;
import com.xtini.mimalo.soundbuttongangedition.Model.TrapStar;
import com.xtini.mimalo.soundbuttongangedition.R;

import java.util.ArrayList;


public class StarChooserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    NavigationView navigationView;
    private boolean doubleBackToExitPressedOnce = false;
    private Toast toast;
    private DrawerLayout drawer;
    private ArrayList<TrapStar> trapStars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_chooser);
        setTitle("Trap Soundboard");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        trapStars = SplashScreenActivity.trapStars;

        setTheCarusel();

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setTheCarusel() {
        HorizontalInfiniteCycleViewPager pager = findViewById(R.id.horizontal_cycle);
        StarChooserAdapter adapter = new StarChooserAdapter(trapStars, getBaseContext(),this);
        pager.setAdapter(adapter);
    }

    public void reloadTheCarusel() {
        setTheCarusel();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            doubleTapForCloseApp();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
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

}
