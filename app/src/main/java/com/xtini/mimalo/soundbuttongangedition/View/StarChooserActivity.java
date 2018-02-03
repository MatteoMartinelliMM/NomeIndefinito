package com.xtini.mimalo.soundbuttongangedition.View;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.xtini.mimalo.soundbuttongangedition.Control.MyAdapter;
import com.xtini.mimalo.soundbuttongangedition.R;

import java.util.ArrayList;
import java.util.List;


public class StarChooserActivity extends AppCompatActivity {
    private ImageButton sfera;
    List<Integer> lstImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_chooser);

        //sfera = findViewById(R.id.sfera);

        /*sfera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = MediaPlayer.create(v.getContext(), R.raw.sferaebbasta_skrt); //NB: questa è una prova è preferibile perchè + mantenbile leggere dati da una dir esterna
                mediaPlayer.start();
            }
        });*/
        initData();
        HorizontalInfiniteCycleViewPager pager = (HorizontalInfiniteCycleViewPager)findViewById(R.id.horizontal_cycle);
        MyAdapter adapter = new MyAdapter(lstImages,getBaseContext());
        pager.setAdapter(adapter);
    }

    private void initData() {
        lstImages.add(R.drawable.sfera_ebbasta);
        lstImages.add(R.drawable.tony_effe);
        lstImages.add(R.drawable.wayne);
    }
}
