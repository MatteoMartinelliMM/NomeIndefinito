package com.xtini.mimalo.soundbuttongangedition.View;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.xtini.mimalo.soundbuttongangedition.R;

public class StarChooserActivity extends AppCompatActivity {
    private ImageButton sfera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_chooser);

        sfera = findViewById(R.id.sfera);

        sfera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = MediaPlayer.create(v.getContext(),R.raw.sferaebbasta_skrt); //NB: questa è una prova è preferibile perchè + mantenbile leggere dati da una dir esterna
                 mediaPlayer.start();
            }
        });
    }
}
