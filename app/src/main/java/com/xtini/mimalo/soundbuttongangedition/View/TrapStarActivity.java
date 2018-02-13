package com.xtini.mimalo.soundbuttongangedition.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xtini.mimalo.soundbuttongangedition.Control.ButtonResViewAdapter;
import com.xtini.mimalo.soundbuttongangedition.R;

import java.util.ArrayList;

import static com.xtini.mimalo.soundbuttongangedition.Control.StarChooserAdapter.TRAP_STAR;

public class TrapStarActivity extends AppCompatActivity {
    private ArrayList<String> btnName;
    private RecyclerView buttonList;
    private GridLayoutManager gm;
    private ButtonResViewAdapter buttonsAdapter;
    private String trapStarName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trap_star);
        getSupportActionBar().hide();
        Intent i = getIntent();
        trapStarName = i.getStringExtra(TRAP_STAR);
        setTitle(trapStarName);
        init();
        buttonList = findViewById(R.id.buttonList);
        gm = new GridLayoutManager(this,3);
        buttonsAdapter = new ButtonResViewAdapter(btnName,trapStarName,this);
        buttonList.setLayoutManager(gm);
        buttonList.setAdapter(buttonsAdapter);



    }

    public void init(){
        btnName = new ArrayList<>();
        btnName.add("Bufu Demmerda");
        btnName.add("skusku");
        btnName.add("Esghere");
        btnName.add("Skrt");
        btnName.add("SktrtSkrt");
        btnName.add("British");
        btnName.add("Bufetti");
        btnName.add("bo");
        btnName.add("ciao");
        btnName.add("prova");
        btnName.add("roma");
        btnName.add("triplo 7");
        btnName.add("Bufu Demmerda");
        btnName.add("skusku");
        btnName.add("Esghere");
        btnName.add("Skrt");
        btnName.add("SktrtSkrt");
        btnName.add("British");
        btnName.add("Bufetti");
        btnName.add("bo");
        btnName.add("ciao");
        btnName.add("prova");
        btnName.add("roma");
        btnName.add("triplo 7");
        btnName.add("Bufu Demmerda");
        btnName.add("skusku");
        btnName.add("Esghere");
        btnName.add("Skrt");
        btnName.add("SktrtSkrt");
        btnName.add("British");
        btnName.add("Bufetti");
        btnName.add("bo");
        btnName.add("ciao");
        btnName.add("prova");
        btnName.add("roma");
        btnName.add("triplo 7");
        btnName.add("ciny");

    }
}
