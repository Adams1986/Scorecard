package com.github.xb10.scorecard;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.github.xb10.scorecard.model.Scorecard;


public class HoleDetailsPlayersFragment extends Fragment {

    private ListView playerListView;
    private ScorecardActivity scorecardActivity;

    private int holeNumber;
    private Scorecard scorecard;
    static final String PLAYER_SELECTED_KEY = "player_selected_key";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hole_details_players, container, false);

        playerListView = (ListView) view.findViewById(R.id.players_playing_list_view);

        getArgs();
        setPlayerList(view);

        return view;
    }

    public interface HoleDetailsPlayersListener{

        public void updateScores(Scorecard scorecard);
    }

    private void setPlayerList(View v) {



        HoleDetailsPlayerAdapter adapter = new HoleDetailsPlayerAdapter(
                v.getContext(), scorecard.getPlayers(), holeNumber);

        playerListView.setAdapter(adapter);

        playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HoleDetailsFragment fragment = new HoleDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(ScorecardRecyclerAdapter.HOLE_SELECTED_KEY, holeNumber);
                bundle.putInt(PLAYER_SELECTED_KEY, position);
                bundle.putSerializable(ScorecardRecyclerAdapter.SCORECARD_KEY, scorecard);
                fragment.setArguments(bundle);



                if(view.getContext() instanceof ScorecardActivity){

                    scorecardActivity = (ScorecardActivity) view.getContext();
                    scorecardActivity.switchContent(fragment, "HoleDetailsPlayers");
                }
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();

        scorecardActivity.onSectionAttached(R.drawable.gb_flag, "Hul " + LookAndFeel.formatInteger(holeNumber+1));
        //scorecard = scorecardActivity.getScorecard();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;


        if (context instanceof Activity) {

            try {
                a = (Activity) context;

                scorecardActivity = (ScorecardActivity) a;
                scorecardActivity.onSectionAttached(R.drawable.gb_flag, "Hul " + LookAndFeel.formatInteger(holeNumber+1));

            }
            catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    private void getArgs(){
        //Getting arguments set from associated activity
        Bundle args = getArguments();

        //If args has value, set textfield. Don't use key! if no args nl
        if (args != null) {

            if (args.getSerializable(ScorecardRecyclerAdapter.SCORECARD_KEY) != null) {
                holeNumber = args.getInt(ScorecardRecyclerAdapter.HOLE_SELECTED_KEY);
                scorecard = ((Scorecard) args.getSerializable(ScorecardRecyclerAdapter.SCORECARD_KEY));
            }

        }
    }
}
