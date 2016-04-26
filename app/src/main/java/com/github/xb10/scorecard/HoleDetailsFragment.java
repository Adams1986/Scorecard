package com.github.xb10.scorecard;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.github.xb10.scorecard.model.Scorecard;

/**
 * Created by ADI on 30-03-2016.
 */
public class HoleDetailsFragment extends Fragment implements View.OnClickListener {

    static String ITEM_SELECTED_KEY = "item_selected_key";
    static String SCORECARD_KEY = "scorecard_key";

    private TextView holeLength;
    private EditText scoreInput;
    private Button btnSaveInput;

    private OnHoleDetailsListener mCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hole_details, container, false);

        holeLength = (TextView) view.findViewById(R.id.length);
        scoreInput = (EditText) view.findViewById(R.id.score_input);
        btnSaveInput = (Button) view.findViewById(R.id.btnSaveInput);

        btnSaveInput.setOnClickListener(this);

        return view;
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

            ((ScorecardActivity) a).onSectionAttached(R.drawable.gb_stats, "Hole View");

            try {
                mCallback = (OnHoleDetailsListener) a;
            }
            catch (ClassCastException e){
                throw new ClassCastException(a.toString() + " must implement OnScorecardSelectedListener");
            }
        }
    }


    //stackoverflow.com/questions/16036572/how-to-pass-values-between-fragments/
    @Override
    public void onStart() {
        super.onStart();

        //Getting arguments set from associated activity
        Bundle args = getArguments();

        //If args has value, set textfield. Don't use key! if no args nl
        if (args != null){

            int position = args.getInt(ITEM_SELECTED_KEY);
            Scorecard scorecard = ((Scorecard) args.getSerializable(SCORECARD_KEY));

            holeLength.setText(String.format("%d" ,scorecard.getClub().getCourses().get(0).getHoles().get(position).getLength()));

/*            if(!scoreInput.getText().toString().equals(""))
                scorecard.getPlayers().get(0).getScores()[position] = Integer.parseInt(scoreInput.getText().toString());*/
        }
        else {

        }
    }

    public interface OnHoleDetailsListener{

        //public void onScorecardSelected (HoleCardViewTemplate holeCardViewTemplate);
        public void onScoreInput(int score);
    }

    @Override
    public void onClick(View view) {

        if(!scoreInput.getText().toString().equals("")) {
            mCallback.onScoreInput(Integer.parseInt(scoreInput.getText().toString()));

            getFragmentManager().popBackStack();
        }
    }
}
