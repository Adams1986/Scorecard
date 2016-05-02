package com.github.xb10.scorecard;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.github.xb10.scorecard.model.Hole;
import com.github.xb10.scorecard.model.Scorecard;

import java.util.ArrayList;


public class ScorecardRecyclerAdapter extends RecyclerView.Adapter<ScorecardRecyclerAdapter.MenuViewHolder> {

    private Scorecard scorecard;
    private boolean isFrontNine;

    public ScorecardRecyclerAdapter(Scorecard scorecard, boolean isFrontNine) {
        this.scorecard = scorecard;
        this.isFrontNine = isFrontNine;
    }


    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView holeNumber;
        TextView par;
        TextView[] playerNameViews;
        Scorecard scorecard;
        boolean isFrontNine;
        int position;

        public MenuViewHolder(final View itemView) {

            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.hole_card_view);
            holeNumber = (TextView) itemView.findViewById(R.id.hole_number);
            par = (TextView) itemView.findViewById(R.id.par_number);

            playerNameViews = new TextView[4];
            playerNameViews[0] = (TextView) itemView.findViewById(R.id.player_one);
            playerNameViews[1] = (TextView) itemView.findViewById(R.id.player_two);
            playerNameViews[2] = (TextView) itemView.findViewById(R.id.player_three);
            playerNameViews[3] = (TextView) itemView.findViewById(R.id.player_four);

/*            for (int i = 0; i < scorecard.getPlayers().size(); i++){

                playerNameViews[i].setText(scorecard.getPlayers().get(i).getFirstName());
            }*/

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch (scorecard.getClub().getCourses().get(0).getHoles().get(position).getNumber()){

                        case 1:
                        default:
                            if(!isFrontNine){
                                //If backnine make sure index is added 9 so right hole
                                position += 9;
                            }
                            fragmentJump(scorecard, position);
                            break;
                    }

                }

                private void fragmentJump(Scorecard scorecard, int position) {
                    HoleDetailsFragment fragment = new HoleDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(HoleDetailsFragment.ITEM_SELECTED_KEY, position);
                    bundle.putSerializable(HoleDetailsFragment.SCORECARD_KEY, scorecard);
                    fragment.setArguments(bundle);



                    if(itemView.getContext() instanceof ScorecardActivity){

                        ScorecardActivity scorecardActivity = (ScorecardActivity) itemView.getContext();
                        scorecardActivity.switchContent(fragment);
                    }
                }
            });
        }


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hole_card_view_template, parent, false);

        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {

        //setting scorecard from view holder class equal to recycler class' scorecard
        holder.scorecard = scorecard;
        holder.position = position;
        holder.isFrontNine = isFrontNine;

        //TODO: multiple courses is a bit awkward
        ArrayList<Hole> holes = null;
        if (isFrontNine) {
            holes = scorecard.getClub().getCourses().get(0).getFrontNine();
        }
        else {
            holes = scorecard.getClub().getCourses().get(0).getBackNine();
        }

        holder.holeNumber.setText(String.format("%d", holes.get(position).getNumber()));
        holder.par.setText(String.format("%d", holes.get(position).getPar()));

        if (!isFrontNine){
            position += 9;
        }

        for (int i = 0; i < scorecard.getPlayers().size(); i++){

            if (scorecard.getPlayers().get(i).getScores()[position] != 0) {
                //getting player number 0-3 (1-4) from i and score 0-17 (1-18) from position
                holder.playerNameViews[i].setText(String.format("%d", scorecard.getPlayers().get(i).getScores()[position]));
            }
        }
    }

    @Override
    public int getItemCount() {

        if (isFrontNine) {
            return scorecard.getClub().getCourses().get(0).getFrontNine().size();
        }
        return scorecard.getClub().getCourses().get(0).getBackNine().size();
    }
}
