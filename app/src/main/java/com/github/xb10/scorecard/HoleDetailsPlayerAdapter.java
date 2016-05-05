package com.github.xb10.scorecard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.github.xb10.scorecard.model.Player;

import java.util.ArrayList;

/**
 * Created by ADI on 26-04-2016.
 */
public class HoleDetailsPlayerAdapter extends ArrayAdapter<Player> {

    private int holeNumber;

    public HoleDetailsPlayerAdapter(Context context, ArrayList<Player> players, int holeNumber) {
        super(context, 0, players);

        this.holeNumber = holeNumber;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Player player = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_hole_details_players, parent, false);
        }

        TextView playerName = (TextView) convertView.findViewById(R.id.list_item_player_name);
        TextView playerHandicap = (TextView) convertView.findViewById(R.id.list_item_player_handicap);
        TextView playerClub = (TextView) convertView.findViewById(R.id.list_item_player_club_membership);
        TextView playerScore = (TextView) convertView.findViewById(R.id.list_item_player_score);


        playerName.setText(player.getFullName());
        playerHandicap.setText(String.format("%.1f", player.getHandicap()));
        //only show one club in list. First index may be arbitrary but all members will have a club in this pos
        playerClub.setText(player.getClubs().get(0));

        //set text to see if score on hole has been registered
        if(player.getScores()[holeNumber] != 0){
            playerScore.setText(LookAndFeel.formatInteger(player.getScores()[holeNumber]));
        }
        else {
            playerScore.setText("-");
        }

        return convertView;
    }
}
