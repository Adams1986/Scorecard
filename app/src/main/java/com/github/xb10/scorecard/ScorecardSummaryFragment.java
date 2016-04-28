package com.github.xb10.scorecard;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.xb10.scorecard.model.Scorecard;

import java.util.ArrayList;


public class ScorecardSummaryFragment extends Fragment {

    //package local as used by other class
    static String SCORECARD_KEY = "scorecard_key";

    private RecyclerView rv;
    private Scorecard scorecard;
    private ScorecardActivity scorecardActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scorecard_summary, container, false);

        //Get scorecard sent from activity
        getArgs();
        initPlayerNames(view);
        initScoreCardRecycler(view);

        return view;
    }

    private void initPlayerNames(View view) {

        TextView[] playerNames = new TextView[4];
        playerNames[0] = (TextView) view.findViewById(R.id.player_one_name);
        playerNames[1] = (TextView) view.findViewById(R.id.player_two_name);
        playerNames[2] = (TextView) view.findViewById(R.id.player_three_name);
        playerNames[3] = (TextView) view.findViewById(R.id.player_four_name);

        for(int i = 0; i < scorecard.getPlayers().size(); i++){
            playerNames[i].setText(scorecard.getPlayers().get(i).getFirstName());
        }
    }

    private void getArgs(){
        //Getting arguments set from associated activity
        Bundle args = getArguments();

        //If args has value, set textfield. Don't use key! if no args nl
        if (args != null) {

            scorecard = ((Scorecard) args.getSerializable(SCORECARD_KEY));
        }
    }

    private void initScoreCardRecycler(View view) {


        rv=(RecyclerView) view.findViewById(R.id.recycler_view);

        CustomLayoutManager layoutManager = new CustomLayoutManager(view.getContext());
        //make not scrollable
        layoutManager.setScrollEnabled(false);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);

        initDataInRecyclerView();
    }

    private void initDataInRecyclerView() {

        ArrayList<String>labels = new ArrayList<>();
        labels.add("Slag");
        labels.add("Point");
        labels.add("Over/under");

        String[][] playerInformation = new String[4][labels.size()];

        for(int i = 0; i < scorecard.getPlayers().size(); i++){

            playerInformation[i][0] = LookAndFeel.formatInteger(scorecard.getPlayers().get(i).getScoreTotal());
            playerInformation[i][1] =
                    LookAndFeel.formatInteger(scorecard.getPlayers().get(i).getTotalPoints(scorecard.getClub().getCourses().get(0)));
            playerInformation[i][2] =
                    LookAndFeel.formatInteger(scorecard.getPlayers().get(i).getUnderOverPar(scorecard.getClub().getCourses().get(0)));
        }

        ScorecardSummaryRecyclerAdapter adapter = new ScorecardSummaryRecyclerAdapter(labels, playerInformation);
        rv.setAdapter(adapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity){

            a = (Activity) context;

            scorecardActivity = (ScorecardActivity) a;
            scorecardActivity.onSectionAttached(R.drawable.gb_summary, "Summary");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        scorecardActivity.onSectionAttached(R.drawable.gb_summary, "Summary");
    }

    //stackoverflow.com/questions/16036572/how-to-pass-values-between-fragments/
    @Override
    public void onStart() {
        super.onStart();

        getArgs();
    }
}
