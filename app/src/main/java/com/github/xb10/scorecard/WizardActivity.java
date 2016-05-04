package com.github.xb10.scorecard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.github.xb10.scorecard.model.*;

import java.util.ArrayList;

public class WizardActivity extends AppCompatActivity implements CourseChooserFragment.CourseChooserListener, PlayerChooserFragment.PlayerChooserListener, PlayerSearchFragment.PlayerSearchListener{


    private Toolbar toolbar;
    private Club selectedClub;
    private Member currentMember;
    private ArrayList<Player> players;

    private PlayerChooserFragment playerChooserFragment;

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

        playerChooserFragment = new PlayerChooserFragment();
        //passing on information to next fragment
        playerChooserFragment.setSelectedParams(currentMember, selectedClub);

        setDefaultPlayers();
        playerChooserFragment.setSelectedPlayers(players);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_wizard, playerChooserFragment).addToBackStack("CourseSearch").commit();

    }

    @Override
    public void startPlaying(Scorecard currentScorecard) {

        Intent intent = new Intent(WizardActivity.this, ScorecardActivity.class);
        intent.putExtra("Scorecard", currentScorecard);
        startActivity(intent);
        finish();

    }

    @Override
    public void startPlayerSearch() {

        PlayerSearchFragment fragment = new PlayerSearchFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(PlayerSearchFragment.CURRENT_PLAYERS_KEY, players);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_wizard, fragment).addToBackStack("PlayerChooser").commit();
    }

    @Override
    public void setPlayers(ArrayList<Player> currentPlayers) {

        playerChooserFragment = new PlayerChooserFragment();
        //passing on information to next fragment
        playerChooserFragment.setSelectedParams(currentMember, selectedClub);
        //setPlayers(player);
        players = currentPlayers;
        setDefaultPlayers();
        playerChooserFragment.setSelectedPlayers(players);
        //getSupportFragmentManager().beginTransaction().replace(R.id.container_wizard, playerChooserFragment).addToBackStack(null).commit();
    }

    private void setDefaultPlayers() {

        if (players == null) {

            int []scores = new int[selectedClub.getCourses().get(0).getHoles().size()];
            players = new ArrayList<>();
            Player currentPlayer = new Player(currentMember, scores);
            players.add(currentPlayer);

            Player dummy = createDummyPlayer();
            players.add(dummy);
        }
        else {
            for(Player temp : players) {

                int []scores = new int[selectedClub.getCourses().get(0).getHoles().size()];
                temp.setScores(scores);
            }
        }
    }

    private Player createDummyPlayer() {
        Player dummy = new Player();
        dummy.setFirstName("Tilføj Ny");
        dummy.setLastName("Spiller");
        dummy.setHandicap(-100);
        ArrayList<String> dummyClub = new ArrayList<>();
        dummyClub.add("");
        dummy.setClubs(dummyClub);

        return dummy;
    }
}
