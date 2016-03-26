package com.github.xb10.scorecard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<MenuOption> options;
    private LinkedList<HoleCardViewTemplate> holes;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initRecyclerView();
        //initScorecard();
    }

    private void initRecyclerView() {

        rv=(RecyclerView)findViewById(R.id.recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initData();
    }

    private void initData() {

        options = new ArrayList<>();
        options.add(new MenuOption("Scorekort", R.drawable.gb_scorekort));
        options.add(new MenuOption("Statistik", R.drawable.gb_stats));
        options.add(new MenuOption("Kasper", R.drawable.k_fly));
        options.add(new MenuOption("Peter", R.drawable.bitch));
        options.add(new MenuOption("C-Dawg", R.drawable.relax));
        options.add(new MenuOption("Simon", R.drawable.boss));
        options.add(new MenuOption("Simon", R.drawable.captain));
        options.add(new MenuOption("Logout", R.drawable.logout));

        RecyclerAdapter menuAdapter = new RecyclerAdapter(options);
        rv.setAdapter(menuAdapter);
    }

    private void initScorecard(){

        rv=(RecyclerView)findViewById(R.id.hole_view);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initScorecardData();
    }

    private void initScorecardData(){

        holes = new LinkedList<>();

        //Player objects instead
        String[]players = new String[]{"Simon", "Sebastian", "Christopher", "Paul"};

        holes.add(new HoleCardViewTemplate(1, 3, new int[]{10,11,14,19}, players));
        holes.add(new HoleCardViewTemplate(2, 5, new int[]{5,6,7,8}, players));
        holes.add(new HoleCardViewTemplate(3, 4, new int[]{5,6,7,8}, players));
        holes.add(new HoleCardViewTemplate(4, 3, new int[]{5,6,7,8}, players));
        holes.add(new HoleCardViewTemplate(5, 4, new int[]{5,6,7,8}, players));
        holes.add(new HoleCardViewTemplate(6, 4, new int[]{5,6,7,8}, players));
        holes.add(new HoleCardViewTemplate(7, 5, new int[]{5,6,7,8}, players));
        holes.add(new HoleCardViewTemplate(8, 3, new int[]{5,6,7,8}, players));
        holes.add(new HoleCardViewTemplate(9, 4, new int[]{8,7,6,5}, players));

        ScorecardRecyclerAdapter menuAdapter = new ScorecardRecyclerAdapter(holes);
        rv.setAdapter(menuAdapter);
    }
}
