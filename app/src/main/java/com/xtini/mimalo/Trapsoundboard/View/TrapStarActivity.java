package com.xtini.mimalo.Trapsoundboard.View;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.xtini.mimalo.Trapsoundboard.Control.ButtonResViewAdapter;
import com.xtini.mimalo.Trapsoundboard.Control.MediaPlayerRegistry;
import com.xtini.mimalo.Trapsoundboard.Control.StarChooserAdapter;
import com.xtini.mimalo.Trapsoundboard.Model.AudioFile;

import com.xtini.mimalo.Trapsoundboard.R;



import java.util.ArrayList;

import static com.xtini.mimalo.Trapsoundboard.Control.StarChooserAdapter.TRAP_STAR;

public class TrapStarActivity extends AppCompatActivity{

    private RecyclerView buttonList;
    private GridLayoutManager gm;
    private ButtonResViewAdapter buttonsAdapter;
    private String trapStarName;
    private ArrayList<AudioFile> audioFiles;
    private RelativeLayout parentLayout;
    private boolean playerIsReleased = false;
    private AudioManager audio;
    private String AdBannerId = "ca-app-pub-7408325265716426/2040619185";
    private AdView AdBanner ;
    private String AdMobAppId = "ca-app-pub-7408325265716426~9273012450";
    private String adMobPubTest = "ca-app-pub-3940256099942544/6300978111";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trap_star);
        getSupportActionBar().hide();
        Intent i = getIntent();
        trapStarName = i.getStringExtra(TRAP_STAR);
        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioFiles = StarChooserAdapter.audioFiles;
        parentLayout = findViewById(R.id.parentLayout);
        buttonList = findViewById(R.id.buttonList);
        gm = new GridLayoutManager(this,3);
        buttonsAdapter = new ButtonResViewAdapter(audioFiles,this, parentLayout);
        buttonList.setLayoutManager(gm);
        buttonList.setAdapter(buttonsAdapter);


        MobileAds.initialize(this, adMobPubTest);
        //Inizializzazione Banner in View
        AdBanner = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdBanner.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        MediaPlayerRegistry.closePlayers(this);
        playerIsReleased = true;
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!playerIsReleased)
            MediaPlayerRegistry.closePlayers(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                return true;
            case KeyEvent.KEYCODE_BACK:
                onBackPressed();
                return true;

            default:
                return false;
        }
    }
}
