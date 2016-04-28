package com.github.xb10.scorecard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ScorecardSummaryRecyclerAdapter extends RecyclerView.Adapter<ScorecardSummaryRecyclerAdapter.HoleDetailsViewHolder> {

    public static class HoleDetailsViewHolder extends RecyclerView.ViewHolder {

        TextView label;
        TextView [] playerStatistics;

        HoleDetailsViewHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.scorecard_summary_label_text_view);
            playerStatistics = new TextView[4];

            playerStatistics[0] = (TextView) itemView.findViewById(R.id.scorecard_summary_player_one_stats);
            playerStatistics[1] = (TextView) itemView.findViewById(R.id.scorecard_summary_player_two_stats);
            playerStatistics[2] = (TextView) itemView.findViewById(R.id.scorecard_summary_player_three_stats);
            playerStatistics[3] = (TextView) itemView.findViewById(R.id.scorecard_summary_player_four_stats);
        }
    }

    private ArrayList<String> labels;
    private String[][] information;

    public ScorecardSummaryRecyclerAdapter(ArrayList<String> labels, String[][] information) {
        this.labels = labels;
        this.information = information;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public HoleDetailsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.table_cell_scorecard_summary, viewGroup, false);

        return new HoleDetailsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HoleDetailsViewHolder holeDetailsViewHolder, int i) {
        holeDetailsViewHolder.label.setText(labels.get(i));
//        holeDetailsViewHolder.playerStatistics[0].setText(information[0][i]);
//        holeDetailsViewHolder.playerStatistics[1].setText(information[1][i]);
//        holeDetailsViewHolder.playerStatistics[2].setText(information[2][i]);
//        holeDetailsViewHolder.playerStatistics[3].setText(information[3][i]);

        for(int count = 0; count < information.length; count++){

            holeDetailsViewHolder.playerStatistics[count].setText(information[count][i]);
        }
    }

    @Override
    public int getItemCount() {
        return labels.size();
    }
}