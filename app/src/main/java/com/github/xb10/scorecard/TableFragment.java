package com.github.xb10.scorecard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import com.github.xb10.scorecard.model.*;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by ADI on 01-04-2016.
 */
public class TableFragment extends Fragment {

    private Scorecard currentScorecard;
    private ScorecardActivity scorecardActivity;
    private CardView cv;

    private TableListener activityCommander;

    public interface TableListener{
        public void openScorecardSummary();
        public Scorecard getScorecard();
        public void endGame(Scorecard currentScorecard);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity){

            a = (Activity) context;
            activityCommander = (TableListener) a;
            currentScorecard = activityCommander.getScorecard();

            scorecardActivity = (ScorecardActivity) a;
            scorecardActivity.onSectionAttached(R.drawable.gb_scorekort, "Scorekort");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        scorecardActivity.onSectionAttached(R.drawable.gb_scorekort, "Scorekort");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.score_table_fragment, container, false);


        setHasOptionsMenu(true);

        initPlayerNames(view);
        initScorecard(R.id.hole_view_front, view, true);

        if (currentScorecard.getClub().getCourses().get(0).getBackNine() != null) {
            initScorecard(R.id.hole_view_back, view, false);
            //initScorecardData(currentScorecard.getClub().getCourses().get(0));
        }

        initDialog(view, savedInstanceState);

        return view;
    }



    private void initDialog(final View view, final Bundle savedInstanceState) {
        cv = (CardView) view.findViewById(R.id.btn_save_scorecard);

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setMessage("Din runde gemmes. Du kan finde den under statistik")
                        .setCancelable(false)
                        .setPositiveButton("Gem", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                activityCommander.endGame(currentScorecard);
                            }
                        }).setNegativeButton("Fortryd", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
            }
        });
    }

    private void initPlayerNames(View view) {

        TextView[] playerNames = new TextView[4];
        playerNames[0] = (TextView) view.findViewById(R.id.player_one_name);
        playerNames[1] = (TextView) view.findViewById(R.id.player_two_name);
        playerNames[2] = (TextView) view.findViewById(R.id.player_three_name);
        playerNames[3] = (TextView) view.findViewById(R.id.player_four_name);

        for(int i = 0; i < currentScorecard.getPlayers().size(); i++){
            playerNames[i].setText(currentScorecard.getPlayers().get(i).getFirstName());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.scorecard, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        activityCommander.openScorecardSummary();

        return super.onOptionsItemSelected(item);
    }

    private void initScorecard(int id, View view, boolean isFrontNine){

        RecyclerView rv = (RecyclerView) view.findViewById(id);

        CustomLayoutManager layoutManager = new CustomLayoutManager(view.getContext());
        layoutManager.setScrollEnabled(false);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);

        ScorecardRecyclerAdapter adapter = new ScorecardRecyclerAdapter(currentScorecard, isFrontNine);
        rv.setAdapter(adapter);

        initFrontAndBackTotals(view);
        initTotals(view);
    }

    private void initTotals(View view) {

        TextView parTotal = (TextView) view.findViewById(R.id.par_total_total);
        parTotal.setText(LookAndFeel.formatInteger(currentScorecard.getClub().getCourses().get(0).getParTotal()));

        TextView[] playerScoreTotals = new TextView[4];
        playerScoreTotals[0] = (TextView) view.findViewById(R.id.player_one_score_total);
        playerScoreTotals[1] = (TextView) view.findViewById(R.id.player_two_score_total);
        playerScoreTotals[2] = (TextView) view.findViewById(R.id.player_three_score_total);
        playerScoreTotals[3] = (TextView) view.findViewById(R.id.player_four_score_total);

        for (int i = 0; i < currentScorecard.getPlayers().size(); i++) {
            if (currentScorecard.getPlayers().get(i).getScoreTotal() != 0) {
                playerScoreTotals[i].setText(LookAndFeel.formatInteger(currentScorecard.getPlayers().get(i).getScoreTotal()));
            }
        }
    }

    private void initFrontAndBackTotals(View view) {

        //Set par total for front and back
        TextView parFrontNineTotal = (TextView) view.findViewById(R.id.par_total_front_nine);
        parFrontNineTotal.setText(LookAndFeel.formatInteger(currentScorecard.getClub().getCourses().get(0).getParFrontNine()));
        TextView parBackNineTotal = (TextView) view.findViewById(R.id.par_total_back_nine);
        parBackNineTotal.setText(LookAndFeel.formatInteger(currentScorecard.getClub().getCourses().get(0).getParBackNine()));

        //Init player textviews
        TextView[] playerFrontNineTotals = new TextView[4];
        playerFrontNineTotals[0] = (TextView) view.findViewById(R.id.player_one_front_nine_total);
        playerFrontNineTotals[1] = (TextView) view.findViewById(R.id.player_two_front_nine_total);
        playerFrontNineTotals[2] = (TextView) view.findViewById(R.id.player_three_front_nine_total);
        playerFrontNineTotals[3] = (TextView) view.findViewById(R.id.player_four_front_nine_total);

        TextView[] playerBackNineTotals = new TextView[4];
        playerBackNineTotals[0] = (TextView) view.findViewById(R.id.player_one_back_nine_total);
        playerBackNineTotals[1] = (TextView) view.findViewById(R.id.player_two_back_nine_total);
        playerBackNineTotals[2] = (TextView) view.findViewById(R.id.player_three_back_nine_total);
        playerBackNineTotals[3] = (TextView) view.findViewById(R.id.player_four_back_nine_total);

        //Set player textviews
        for (int i = 0; i < currentScorecard.getPlayers().size(); i++){
            if (currentScorecard.getPlayers().get(i).getFrontNineTotal() > 0) {
                playerFrontNineTotals[i].setText(LookAndFeel.formatInteger(currentScorecard.getPlayers().get(i).getFrontNineTotal()));
            }

            if (currentScorecard.getClub().getCourses().get(0).getHoles().size() == 18){
                if (currentScorecard.getPlayers().get(i).getBackNineTotal() > 0) {
                    playerBackNineTotals[i].setText(LookAndFeel.formatInteger(currentScorecard.getPlayers().get(i).getBackNineTotal()));
                }
            }
        }
    }

    //Data about the holes for future use in Google Maps functionality
    private void initScorecardData(Course course){


        //TODO: Perspective latlong!!!!!!!!
        CourseMetaData hk18Metadata = new CourseMetaData(new float[]{
                0f, -90f, 100f, 0f, 180f, -10f, -100f, 190f, 190f,
                180f, 0f, 180f, 0f, 200f, 200f, 30f, 90f, 120f},
                new float[]{
                        18f, 16.5f, 16.7f, 18f, 17f, 17.1f, 16.6f, 18.2f, 17f,
                        17f, 16.9f, 16.8f, 16.4f, 16.7f, 18.3f, 16.3f, 18.5f, 17.1f
                },
                new LatLng[]{
                        new LatLng(55.489252, 12.104942), new LatLng(55.490379, 12.104108), new LatLng(55.490778, 12.097419),
                        new LatLng(55.490882, 12.104269), new LatLng(55.492428, 12.105054), new LatLng(55.489797, 12.106511),
                        new LatLng(55.492435, 12.103754), new LatLng(55.491313, 12.097111), new LatLng(55.488979, 12.096060),
                        new LatLng(55.486118, 12.095676), new LatLng(55.483126, 12.093985), new LatLng(55.486615, 12.094512),
                        new LatLng(55.484519, 12.092482), new LatLng(55.488833, 12.094660), new LatLng(55.485873, 12.091394),
                        new LatLng(55.485519, 12.090043), new LatLng(55.489238, 12.093748), new LatLng(55.489681, 12.098958),
                },
                new LatLng[]{
                        new LatLng(55.490553, 12.104757), new LatLng(55.490444, 12.097302), new LatLng(55.490728, 12.103651),
                        new LatLng(55.492255, 12.104291), new LatLng(55.489439, 12.105393), new LatLng(55.492370, 12.105671),
                        new LatLng(55.491104, 12.097506), new LatLng(55.490097, 12.096691), new LatLng(55.486355, 12.095303),
                        new LatLng(55.483139, 12.094606), new LatLng(55.485895, 12.094973), new LatLng(55.483497, 12.093314),
                        new LatLng(55.488623, 12.095139), new LatLng(55.485694, 12.092076), new LatLng(55.484979, 12.090102),
                        new LatLng(55.489033, 12.094142), new LatLng(55.489511, 12.095471), new LatLng(55.489089, 12.104031),
                });

        course.setMetaData(hk18Metadata);
    }
}
