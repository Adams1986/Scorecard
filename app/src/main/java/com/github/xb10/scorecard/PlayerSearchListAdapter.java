package com.github.xb10.scorecard;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.github.xb10.scorecard.model.Player;

import java.util.ArrayList;

/**
 * Created by ADI on 26-04-2016.
 */
public class PlayerSearchListAdapter extends ArrayAdapter<Player> {

    ArrayList<Player>players;

    public PlayerSearchListAdapter(Context context, ArrayList<Player> players) {
        super(context, 0, players);

        this.players = players;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Player player = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_player_search, parent, false);
        }

        TextView playerName = (TextView) convertView.findViewById(R.id.list_item_player_name);
        TextView playerHandicap = (TextView) convertView.findViewById(R.id.list_item_player_handicap);
        TextView playerClub = (TextView) convertView.findViewById(R.id.list_item_player_club_membership);


        playerName.setText(player.getFullName());
        //small hack to remove dummy text
        if (player.getHandicap() != -100) {
            playerHandicap.setText(String.format("%.1f", player.getHandicap()));
        }
        //only show one club in list. First index may be arbitrary but all members will have a club in this pos
        playerClub.setText(player.getClubs().get(0));


        return convertView;
    }
}
