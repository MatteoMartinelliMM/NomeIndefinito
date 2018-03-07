package com.xtini.mimalo.soundbuttongangedition.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.xtini.mimalo.soundbuttongangedition.Control.ButtonResViewAdapter;
import com.xtini.mimalo.soundbuttongangedition.Control.MediaPlayerRegistry;
import com.xtini.mimalo.soundbuttongangedition.Control.StarChooserAdapter;
import com.xtini.mimalo.soundbuttongangedition.Model.AudioFile;
import com.xtini.mimalo.soundbuttongangedition.R;

import java.util.ArrayList;

import static com.xtini.mimalo.soundbuttongangedition.Control.StarChooserAdapter.TRAP_STAR;

public class TrapStarActivity extends AppCompatActivity {
    private RecyclerView buttonList;
    private GridLayoutManager gm;
    private ButtonResViewAdapter buttonsAdapter;
    private String trapStarName;
    private ArrayList<AudioFile> audioFiles;
    private RelativeLayout parentLayout;
    private boolean playerIsReleased = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trap_star);
        getSupportActionBar().hide();
        Intent i = getIntent();
        trapStarName = i.getStringExtra(TRAP_STAR);
        setTitle(trapStarName);
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
    protected void onPause() {
        super.onPause();
        if(!playerIsReleased)
            MediaPlayerRegistry.closePlayers(this);
    }
}
