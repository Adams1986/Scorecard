package com.github.xb10.scorecard;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ScorecardRecyclerAdapter extends RecyclerView.Adapter<ScorecardRecyclerAdapter.MenuViewHolder> {

    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView holeNumber;
        TextView par;
        TextView[]players;
        /*TextView playerOne;
        TextView playerTwo;
        TextView playerThree;
        TextView playerFour;*/
        HoleCardViewTemplate currentItem;

        MenuViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.hole_card_view);
            holeNumber = (TextView) itemView.findViewById(R.id.hole_number);
            par = (TextView) itemView.findViewById(R.id.par_number);

            players = new TextView[4];
            players[0] = (TextView) itemView.findViewById(R.id.player_one);
            players[1] = (TextView) itemView.findViewById(R.id.player_two);
            players[2] = (TextView) itemView.findViewById(R.id.player_three);
            players[3] = (TextView) itemView.findViewById(R.id.player_four);

            /*playerOne = (TextView) itemView.findViewById(R.id.player_one);
            playerTwo = (TextView) itemView.findViewById(R.id.player_two);
            playerThree = (TextView) itemView.findViewById(R.id.player_three);
            playerFour = (TextView) itemView.findViewById(R.id.player_four);*/

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch (currentItem.getHoleNumber()){

                        case 1:
                            Toast.makeText(view.getContext(), "Bitch work for you", Toast.LENGTH_LONG).show();
                            break;
                        case 2:
                            //Intent i = new Intent(view.getContext(), LoginActivity.class);
                            view.getContext().startActivity(new Intent(view.getContext(), LoginActivity.class));
                            break;
                    }
                }
            });
        }

        interface onClickListener{

        }
    }

    private List<HoleCardViewTemplate> holes;

    ScorecardRecyclerAdapter(List<HoleCardViewTemplate> holes){
        this.holes = holes;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hole_card_view_template, viewGroup, false);

        return new MenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder menuViewHolder, int i) {

        menuViewHolder.holeNumber.setText(String.format("%d", holes.get(i).getHoleNumber()));

        menuViewHolder.par.setText(String.format("%d", holes.get(i).getPar()));

        for (int j = 0; j < holes.get(i).getPlayers().length; j++){

            menuViewHolder.players[j].setText(String.format("%d",holes.get(i).getScores()[j]));
        }

        /*menuViewHolder.playerOne.setText(holes.get(i).getPar());
        menuViewHolder.playerTwo.setText(holes.get(i).getPar());
        menuViewHolder.playerThree.setText(holes.get(i).getPar());
        menuViewHolder.playerFour.setText(holes.get(i).getPar());*/

        menuViewHolder.currentItem = holes.get(i);
    }

    @Override
    public int getItemCount() {
        return holes.size();
    }
}
