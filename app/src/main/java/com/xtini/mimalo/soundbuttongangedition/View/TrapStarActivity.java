package com.xtini.mimalo.soundbuttongangedition.View;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import com.xtini.mimalo.soundbuttongangedition.Control.ButtonResViewAdapter;
import com.xtini.mimalo.soundbuttongangedition.Control.MediaPlayerRegistry;
import com.xtini.mimalo.soundbuttongangedition.Control.StarChooserAdapter;
import com.xtini.mimalo.soundbuttongangedition.Model.AudioFile;

import com.xtini.mimalo.soundbuttongangedition.R;



import java.util.ArrayList;

import static com.xtini.mimalo.soundbuttongangedition.Control.StarChooserAdapter.TRAP_STAR;

public class TrapStarActivity extends AppCompatActivity{
    public static int howManyClick;
    private RecyclerView buttonList;
    private GridLayoutManager gm;
    private ButtonResViewAdapter buttonsAdapter;
    private String trapStarName;
    private ArrayList<AudioFile> audioFiles;
    private RelativeLayout parentLayout;
    private boolean playerIsReleased = false;
    private AudioManager audio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trap_star);
        getSupportActionBar().hide();
        Intent i = getIntent();
        howManyClick = StarChooserActivity.howManyClick;
        trapStarName = i.getStringExtra(TRAP_STAR);
        setTitle(trapStarName);
        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioFiles = StarChooserAdapter.audioFiles;
        parentLayout = findViewById(R.id.parentLayout);
        buttonList = findViewById(R.id.buttonList);
        gm = new GridLayoutManager(this,3);
        buttonsAdapter = new ButtonResViewAdapter(audioFiles,this, parentLayout);
        buttonList.setLayoutManager(gm);
        buttonList.setAdapter(buttonsAdapter);
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
    protected void onStop() {
        super.onStop();
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
