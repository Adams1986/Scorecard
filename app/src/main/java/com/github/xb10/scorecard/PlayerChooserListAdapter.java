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
public class PlayerChooserListAdapter extends ArrayAdapter<Player> {

    private int selectedPos = 0;
    private int index = 0;

    public PlayerChooserListAdapter(Context context, ArrayList<Player> players) {
        super(context, 0, players);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Player player = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_player_chooser, parent, false);
        }

        TextView playerName = (TextView) convertView.findViewById(R.id.list_item_player_name);
        TextView playerHandicap = (TextView) convertView.findViewById(R.id.list_item_player_handicap);
        TextView playerClub = (TextView) convertView.findViewById(R.id.list_item_player_club_membership);
        final RadioButton rdBtn = (RadioButton) convertView.findViewById(R.id.radioButton);
        final ImageButton imgBtn = (ImageButton) convertView.findViewById(R.id.imageButton);
        rdBtn.setChecked(position == selectedPos);
        rdBtn.setTag(position);

        rdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPos = (Integer) v.getTag();

                //notify player that he is playing without a marker
                if(selectedPos == 0){
                    Toast.makeText(v.getContext(), "Du har valgt ikke at spille med markør", Toast.LENGTH_LONG).show();
                }
                notifyDataSetChanged();
            }
        });

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Method will eventually change the length of the holes that is played
                switch (index){
                    case 0:
                        imgBtn.setBackgroundColor(Color.RED);
                        index++;
                        break;

                    case 1:
                        imgBtn.setBackgroundColor(Color.WHITE);
                        index++;
                        break;

                    case 2:
                        imgBtn.setBackgroundColor(Color.YELLOW);
                        index = 0;
                        break;

                }

                Toast.makeText(getContext(), "Vælg hvor du vil spille fra:", Toast.LENGTH_SHORT).show();
            }
        });

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
