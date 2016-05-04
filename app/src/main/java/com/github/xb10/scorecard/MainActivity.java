package com.github.xb10.scorecard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.github.xb10.scorecard.model.Member;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Member currentMember;
    private List<MenuOption> options;
    private LinkedList<HoleCardViewTemplate> holes;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        LookAndFeel.setToolbarLogo(toolbar, MainActivity.this, R.drawable.boss, getString(R.string.main_menu_header));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (currentMember != null) {
            Intent intent = new Intent(MainActivity.this, MemberInfoActivity.class);
            intent.putExtra("currentMember", currentMember);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView() {

        rv=(RecyclerView)findViewById(R.id.recycler_view);

        CustomLayoutManager layoutManager = new CustomLayoutManager(this);
        //make not scrollable
        layoutManager.setScrollEnabled(false);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);

        initData();
    }

    private void initData() {

        if (getIntent().getSerializableExtra("currentMember") != null) {
            options = new ArrayList<>();
            options.add(new MenuOption("Scorekort", R.drawable.gb_scorekort));
            options.add(new MenuOption("Statistik", R.drawable.gb_stats));
            options.add(new MenuOption("Til godkendelse", R.drawable.gb_marker_approv));
            options.add(new MenuOption("Afstandsmåler", R.drawable.gb_distance_measurer));
            options.add(new MenuOption("Logout", R.drawable.logout));

            currentMember = (Member) getIntent().getSerializableExtra("currentMember");

            MenuRecyclerAdapter menuAdapter = new MenuRecyclerAdapter(options, currentMember);
            rv.setAdapter(menuAdapter);
        }
    }
}
