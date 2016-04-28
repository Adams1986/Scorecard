package com.github.xb10.scorecard;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.xb10.scorecard.model.Member;
import com.github.xb10.scorecard.model.Scorecard;

import java.util.ArrayList;
import java.util.List;

public class HoleDetailsRecyclerAdapter extends RecyclerView.Adapter<HoleDetailsRecyclerAdapter.HoleDetailsViewHolder> {

    public static class HoleDetailsViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView label;
        TextView information;

        HoleDetailsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.hole_details_card_view);
            label = (TextView) itemView.findViewById(R.id.hole_details_label_text_view);
            information = (TextView) itemView.findViewById(R.id.hole_details_info_text_view);
        }
    }

    private ArrayList<String> labels;
    private ArrayList<String> information;

    public HoleDetailsRecyclerAdapter(ArrayList<String> labels, ArrayList<String> information) {
        this.labels = labels;
        this.information = information;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public HoleDetailsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.table_cell_hole_details, viewGroup, false);

        return new HoleDetailsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HoleDetailsViewHolder holeDetailsViewHolder, int i) {
        holeDetailsViewHolder.label.setText(labels.get(i));
        holeDetailsViewHolder.information.setText(information.get(i));
    }

    @Override
    public int getItemCount() {
        return labels.size();
    }
}