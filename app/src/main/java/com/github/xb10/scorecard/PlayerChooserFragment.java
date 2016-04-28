package com.github.xb10.scorecard;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.xb10.scorecard.model.*;

import java.util.ArrayList;
import java.util.Iterator;


public class PlayerChooserFragment extends Fragment {


    private Member currentMember;
    private Club selectedClub;
    private PlayerChooserListener activityCommander;
    private int[] courseSize;
    private ArrayList<Player>players;
    private Player dummy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_player_chooser, container, false);

        TextView tv = (TextView) view.findViewById(R.id.course_player_chooser_fragment);
        CardView finishWizardBtn = (CardView) view.findViewById(R.id.btn_finish_scorecard_wizard);
        tv.setText(selectedClub.getName());
        courseSize = new int[selectedClub.getCourses().get(0).getHoles().size()];

        //TODO: add players instead of creating new arraylist
        players = new ArrayList<>();
        Player currentPlayer = new Player(currentMember, courseSize);
        players.add(currentPlayer);

        dummy = createDummyPlayer();
        players.add(dummy);

        final ListView playerList = (ListView) view.findViewById(R.id.players_chosen_list);
        PlayerChooserListAdapter adapter = new PlayerChooserListAdapter(view.getContext(), players);
        playerList.setAdapter(adapter);


        playerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: involve activity and go to player search
                Toast.makeText(getContext(), "Tilføj spiller " + (position+1), Toast.LENGTH_SHORT).show();
            }
        });

        finishWizardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //iterator to delete element while iterating
                Iterator<Player>i = players.iterator();

                while (i.hasNext()){

                    Player temp = i.next();

                    if (temp.getHandicap() == dummy.getHandicap()){
                        i.remove();
                    }
                }

                Scorecard scorecard = new Scorecard();
                scorecard.setPlayers(players);
                scorecard.setClub(selectedClub);

                activityCommander.startPlaying(scorecard);
            }
        });


        return view;
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

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            activityCommander = (PlayerChooserListener) activity;
            ((WizardActivity) activity).onSectionAttached(R.drawable.gb_nav_bg_golfer, "Tilføj spillere");
        }
        catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (Build.VERSION.SDK_INT < 23) {
            if(context instanceof Activity){

                try{
                    a = (Activity) context;
                    activityCommander = (PlayerChooserListener) a;

                    //TODO: remove probably
                    ((WizardActivity) a).onSectionAttached(R.drawable.gb_nav_bg_golfer, "Tilføj spillere");
                }
                catch (ClassCastException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public interface PlayerChooserListener {
        public void startPlaying(Scorecard currentScorecard);
    }

    public void setSelectedParams(Member currentMember, Club selectedClub){

        //TODO: maybe create static PlayerChooserFragment newInstance with above params instead
        this.currentMember = currentMember;
        this.selectedClub = selectedClub;
    }
}
