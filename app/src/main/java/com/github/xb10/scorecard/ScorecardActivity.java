package com.github.xb10.scorecard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.github.xb10.scorecard.model.Player;
import com.github.xb10.scorecard.model.Scorecard;

import java.util.ArrayList;
import java.util.LinkedList;

public class ScorecardActivity extends AppCompatActivity {


    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scorecard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Last param not working...
        LookAndFeel.setToolbarLogo(toolbar, ScorecardActivity.this, R.drawable.gb_scorekort, "Test");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.container, new TableFragment()).commit();
    }

    //TODO: initialize scorecard for players and course
    private void initPlayers(){

    }

    private void initCourse(){


    }

    //TODO initialise data
    private void initScores(){

    }
    private void initPoints(){

        //getIntent
    }

    public void onSectionAttached(int id, String title) {

        LookAndFeel.setToolbarLogo(toolbar ,ScorecardActivity.this, id, title);
    }

    void switchContent(Fragment holeDetailsFragment){

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, holeDetailsFragment).commit();
    }

    public void setPlayerName(String name) {

        TextView textView = (TextView) findViewById(R.id.player_one_name);
        textView.setText(name);
    }
}
