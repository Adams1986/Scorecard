package com.github.xb10.scorecard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.github.xb10.scorecard.model.Club;
import com.github.xb10.scorecard.model.Course;
import com.github.xb10.scorecard.model.Member;
import com.github.xb10.scorecard.model.Scorecard;

public class WizardActivity extends AppCompatActivity implements CourseChooserFragment.CourseChooserListener, PlayerChooserFragment.PlayerChooserListener{


    private Toolbar toolbar;
    private Club selectedClub;
    private Member currentMember;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wizard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Last param not working...
        LookAndFeel.setToolbarLogo(toolbar, WizardActivity.this, R.drawable.gb_location_icon, "Test");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //get the currentMembers information from previous activity
        setCurrentMember();

        CourseChooserFragment fragment = new CourseChooserFragment();
        fragment.setCurrentMember(currentMember);
        //Start course chooser fragment
        getSupportFragmentManager().beginTransaction().add(R.id.container_wizard, fragment).commit();
    }

    private void setCurrentMember() {

        if (getIntent().getSerializableExtra("currentMember") != null){

            currentMember = (Member) getIntent().getSerializableExtra("currentMember");
        }
    }


    public void onSectionAttached(int id, String title) {

        LookAndFeel.setToolbarLogo(toolbar ,WizardActivity.this, id, title);
    }

    @Override
    public void setClub(Club club) {
        //set selected club to be used when start new activity (scorecardactivity)
        selectedClub = club;

        PlayerChooserFragment fragment = new PlayerChooserFragment();
        //passing on information to next fragment
        fragment.setSelectedParams(currentMember, selectedClub);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_wizard, fragment).addToBackStack("CourseSearch").commit();

    }

    @Override
    public void startPlaying(Scorecard currentScorecard) {

        Intent intent = new Intent(WizardActivity.this, ScorecardActivity.class);
        intent.putExtra("Scorecard", currentScorecard);
        startActivity(intent);
        finish();

    }
}
