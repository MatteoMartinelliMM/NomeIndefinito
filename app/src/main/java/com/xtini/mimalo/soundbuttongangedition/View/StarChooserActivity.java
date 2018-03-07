package com.xtini.mimalo.soundbuttongangedition.View;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import java.util.List;

public class StarChooserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    List<Integer> lstImages = new ArrayList<>();
    List<String> lstNomi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_chooser);
        setTitle("TrapSoundboard");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ArrayList<TrapStar> trapStars = SplashScreenActivity.trapStars;
        initData();
        HorizontalInfiniteCycleViewPager pager = (HorizontalInfiniteCycleViewPager)findViewById(R.id.horizontal_cycle);
        StarChooserAdapter adapter = new StarChooserAdapter(trapStars,getBaseContext());
        pager.setAdapter(adapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
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
                startActivity(new Intent(this,ChiSiamoActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initData() {
        lstImages.add(R.drawable.sferaebbasta);
        lstImages.add(R.drawable.tonyeffe);
        lstImages.add(R.drawable.darkwayne);
        lstNomi.add("Sfera Ebbasta");
        lstNomi.add("Tony Effe");
        lstNomi.add("Dark Wayne");
    }

    public void rateUs()
    {
        Uri uri = Uri.parse("https://play.google.com/store/music/artist/Sfera_Ebbasta" /*+ this.getPackageName()*/);
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try
        {
            this.startActivity(Intent.createChooser(myAppLinkToMarket, "Rate us"));
        }
        catch (ActivityNotFoundException e)
        {
            Toast.makeText(this, " Sorry, Not able to open!", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareApp(){
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String sAux = "\nSe non la scarichi sei solo uno stupido BUFU!\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=Orion.Soft \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivityForResult(Intent.createChooser(i, "Condividi CIUCCIONE!"),0);
        } catch(Exception e) {
            //e.toString();
        }
    }

    public void adviceSound(){
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
