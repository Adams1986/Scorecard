package com.github.xb10.scorecard;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.github.xb10.scorecard.model.Scorecard;

import java.util.ArrayList;

/**
 * Created by ADI on 30-03-2016.
 */
public class HoleDetailsFragment extends Fragment implements View.OnClickListener {

    private static final int DEFAULT_POINTS = 2;

    private RecyclerView rv;
    private EditText scoreEditText;
    private TextView pointsTextView;
    private Button btnPlus;
    private Button btnMinus;
    private CardView btnSave;

    private Scorecard scorecard;
    private int holeNumber;
    private int points;
    private int score;
    private int playerSelected;

    private OnHoleDetailsListener mCallback;
    private ScorecardActivity scorecardActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hole_details, container, false);

        //Get scorecard sent from activity
        getArgs();
        initHoleRecyclerView(view);

        scoreEditText = (EditText) view.findViewById(R.id.hole_details_score_edit_view);
        pointsTextView = (TextView) view.findViewById(R.id.hole_points_info_text_view);
        btnPlus = (Button) view.findViewById(R.id.hole_details_btn_plus);
        btnMinus = (Button) view.findViewById(R.id.hole_details_btn_minus);
        btnSave = (CardView) view.findViewById(R.id.btn_save_input);

        //Calculating holePar + SPH to set the starting amount on the hole for better UX
        if (scorecard.getPlayers().get(playerSelected).getScores()[holeNumber] == 0) {

            score = scorecard.getPlayers().get(playerSelected).getStrokes(scorecard.getClub().getCourses().get(0))[holeNumber]
                    + scorecard.getClub().getCourses().get(0).getHoles().get(holeNumber).getPar();
            points = DEFAULT_POINTS;
        }
        else {
            score = scorecard.getPlayers().get(playerSelected).getScores()[holeNumber];
            points = scorecard.getPlayers().get(playerSelected).getPoints(scorecard.getClub().getCourses().get(0), holeNumber);
        }


        scoreEditText.setText(String.format("%d", score));
        pointsTextView.setText(String.format("%d", points));
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        return view;
    }

    private void getArgs(){
        //Getting arguments set from associated activity
        Bundle args = getArguments();

        //If args has value, set textfield. Don't use key! if no args nl
        if (args != null) {

            holeNumber = args.getInt(ScorecardRecyclerAdapter.HOLE_SELECTED_KEY);
            scorecard = ((Scorecard) args.getSerializable(ScorecardRecyclerAdapter.SCORECARD_KEY));

            playerSelected = args.getInt(HoleDetailsPlayersFragment.PLAYER_SELECTED_KEY);

        }
    }

    private void initHoleRecyclerView(View view) {

        TextView playerTV = (TextView) view.findViewById(R.id.hole_details_player_name);
        playerTV.setText(scorecard.getPlayers().get(playerSelected).getFullName());

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

        labels.add("Hul");
        labels.add("Længde");
        labels.add("Par");
        labels.add("HCP");
        labels.add("SPH");

        ArrayList<String>information = new ArrayList<>();
        information.add(LookAndFeel.formatInteger(scorecard.getClub().getCourses().get(0).getHoles().get(holeNumber).getNumber()));
        information.add(String.format("%dm", scorecard.getClub().getCourses().get(0).getHoles().get(holeNumber).getLength()));
        information.add(String.format("%d", scorecard.getClub().getCourses().get(0).getHoles().get(holeNumber).getPar()));
        information.add(String.format("%d", scorecard.getClub().getCourses().get(0).getHoles().get(holeNumber).getHcp()));
        information.add(String.format("%d", scorecard.getPlayers().get(playerSelected).getStrokes(scorecard.getClub().getCourses().get(0))[holeNumber]));

        HoleDetailsRecyclerAdapter adapter = new HoleDetailsRecyclerAdapter(labels, information);
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
            scorecardActivity.onSectionAttached(R.drawable.gb_flag, "Huloversigt");

            try {
                mCallback = (OnHoleDetailsListener) a;
            }
            catch (ClassCastException e){
                throw new ClassCastException(a.toString() + " must implement OnHoleDetailsListener");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        scorecardActivity.onSectionAttached(R.drawable.gb_flag, "Huloversigt");
    }
/*
    //stackoverflow.com/questions/16036572/how-to-pass-values-between-fragments/
    @Override
    public void onStart() {
        super.onStart();

        //Getting arguments set from associated activity
        Bundle args = getArguments();

        if (args != null){

            holeNumber = args.getInt(ScorecardRecyclerAdapter.HOLE_SELECTED_KEY);
            scorecard = ((Scorecard) args.getSerializable(ScorecardRecyclerAdapter.SCORECARD_KEY));
        }
    }*/

    public interface OnHoleDetailsListener{

        //public void onScorecardSelected (HoleCardViewTemplate holeCardViewTemplate);
        public void onScoreInput(int score, int position, int playerSelected);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.hole_details_btn_plus:
                if (points > 0) {
                    pointsTextView.setText(String.format("%d", --points));
                }
                scoreEditText.setText(String.format("%d", ++score));

                break;

            case R.id.hole_details_btn_minus:
                if (score > 1 && points > 0) {
                    scoreEditText.setText(String.format("%d", --score));
                    pointsTextView.setText(String.format("%d", ++points));
                }
                else if(score > 1 && points == 0){
                    scoreEditText.setText(String.format("%d", --score));
                    scorecard.getPlayers().get(playerSelected).getScores()[holeNumber] = score;
                    points = scorecard.getPlayers().get(0).getPoints(scorecard.getClub().getCourses().get(0), holeNumber);

                    if(points > 0){
                        pointsTextView.setText(String.format("%d", points));
                    }
                }
                break;

            case R.id.btn_save_input:
                mCallback.onScoreInput(Integer.parseInt(scoreEditText.getText().toString()), holeNumber, playerSelected);
                getFragmentManager().popBackStack();
                break;
        }

        /*if(!scoreInput.getText().toString().equals("")) {
            mCallback.onScoreInput(Integer.parseInt(scoreInput.getText().toString()));

            getFragmentManager().popBackStack();
        }*/
    }
}
