package com.xtini.mimalo.soundbuttongangedition.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xtini.mimalo.soundbuttongangedition.R;

import static com.xtini.mimalo.soundbuttongangedition.Control.MyAdapter.TRAP_STAR;

public class TrapStarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trap_star);
        Intent i = getIntent();
        String trapStarName = i.getStringExtra(TRAP_STAR);
        setTitle(trapStarName);

    }
}
