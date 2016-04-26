package com.github.xb10.scorecard;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;
import com.github.xb10.scorecard.model.*;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by ADI on 01-04-2016.
 */
public class TableFragment extends Fragment {

    private int holeInput;

    private Scorecard currentScorecard;
    private RecyclerView rv;

    private TableListener activityCommander;

    public interface TableListener{
        public void openScorecardSummary();
        public Scorecard getScorecard();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity){

            a = (Activity) context;
            activityCommander = (TableListener) a;
            currentScorecard = activityCommander.getScorecard();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.score_table_fragment, container, false);
        TextView[] playerNames = new TextView[4];
        playerNames[0] = (TextView) view.findViewById(R.id.player_one_name);
        playerNames[1] = (TextView) view.findViewById(R.id.player_two_name);
        playerNames[2] = (TextView) view.findViewById(R.id.player_three_name);
        playerNames[3] = (TextView) view.findViewById(R.id.player_four_name);

        for(int i = 0; i < currentScorecard.getPlayers().size(); i++){
            playerNames[i].setText(currentScorecard.getPlayers().get(i).getFirstName());
        }

        setHasOptionsMenu(true);

        initScorecard(R.id.hole_view_front, view, currentScorecard.getClub().getCourses().get(0).getFrontNine());

        if (currentScorecard.getClub().getCourses().get(0).getBackNine() != null) {
            initScorecard(R.id.hole_view_back, view, currentScorecard.getClub().getCourses().get(0).getBackNine());
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.scorecard, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        activityCommander.openScorecardSummary();

        return super.onOptionsItemSelected(item);
    }

    private void initScorecard(int id, View view, ArrayList<Hole>holes){

        rv=(RecyclerView) view.findViewById(id);

        CustomLayoutManager layoutManager = new CustomLayoutManager(view.getContext());
        layoutManager.setScrollEnabled(false);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);

        //Stupid workaround
        Scorecard temp = new Scorecard();

        Course tempCourse = new Course(currentScorecard.getClub().getCourses().get(0).getName(), holes,
                currentScorecard.getClub().getCourses().get(0).getSlope(),
                currentScorecard.getClub().getCourses().get(0).getRating(),
                currentScorecard.getClub().getCourses().get(0).getMetaData());
        ArrayList<Course>tempCourses = new ArrayList<>();
        tempCourses.add(tempCourse);

        Club tempClub = new Club();
        tempClub.setCourses(tempCourses);
        tempClub.setId(currentScorecard.getClub().getId());
        tempClub.setName(currentScorecard.getClub().getName());
        temp.setClub(tempClub);

        temp.setPlayers(currentScorecard.getPlayers());
        temp.getClub().setCourses(tempCourses);


        ScorecardRecyclerAdapter adapter = new ScorecardRecyclerAdapter(temp);
        rv.setAdapter(adapter);
    }


    private void initScorecardData(ArrayList<Hole> holes){


        //TODO: Perspective latlong!!!!!!!!
        CourseMetaData hk18Metadata = new CourseMetaData(new float[]{
                0f, -90f, 100f, 0f, 180f, -10f, -100f, 190f, 190f,
                180f, 0f, 180f, 0f, 200f, 200f, 30f, 90f, 120f},
                new float[]{
                        18f, 16.5f, 16.7f, 18f, 17f, 17.1f, 16.6f, 18.2f, 17f,
                        17f, 16.9f, 16.8f, 16.4f, 16.7f, 18.3f, 16.3f, 18.5f, 17.1f
                },
                new LatLng[]{
                        new LatLng(55.489252, 12.104942), new LatLng(55.490379, 12.104108), new LatLng(55.490778, 12.097419),
                        new LatLng(55.490882, 12.104269), new LatLng(55.492428, 12.105054), new LatLng(55.489797, 12.106511),
                        new LatLng(55.492435, 12.103754), new LatLng(55.491313, 12.097111), new LatLng(55.488979, 12.096060),
                        new LatLng(55.486118, 12.095676), new LatLng(55.483126, 12.093985), new LatLng(55.486615, 12.094512),
                        new LatLng(55.484519, 12.092482), new LatLng(55.488833, 12.094660), new LatLng(55.485873, 12.091394),
                        new LatLng(55.485519, 12.090043), new LatLng(55.489238, 12.093748), new LatLng(55.489681, 12.098958),
                },
                new LatLng[]{
                        new LatLng(55.490553, 12.104757), new LatLng(55.490444, 12.097302), new LatLng(55.490728, 12.103651),
                        new LatLng(55.492255, 12.104291), new LatLng(55.489439, 12.105393), new LatLng(55.492370, 12.105671),
                        new LatLng(55.491104, 12.097506), new LatLng(55.490097, 12.096691), new LatLng(55.486355, 12.095303),
                        new LatLng(55.483139, 12.094606), new LatLng(55.485895, 12.094973), new LatLng(55.483497, 12.093314),
                        new LatLng(55.488623, 12.095139), new LatLng(55.485694, 12.092076), new LatLng(55.484979, 12.090102),
                        new LatLng(55.489033, 12.094142), new LatLng(55.489511, 12.095471), new LatLng(55.489089, 12.104031),
                });
    }
}
