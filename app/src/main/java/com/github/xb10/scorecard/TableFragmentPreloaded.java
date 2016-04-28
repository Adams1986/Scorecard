package com.github.xb10.scorecard;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;
import com.github.xb10.scorecard.model.*;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by ADI on 01-04-2016.
 */
public class TableFragmentPreloaded extends Fragment {

    private int holeInput;

    private Scorecard currentScorecard;
    private RecyclerView rv;
    private Player simon;

    private TableListener activityCommander;

    public interface TableListener{
        public void openScorecardSummary();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity){

            a = (Activity) context;
            activityCommander = (TableListener) a;
//            ((ScorecardActivity) a).setPlayerName("Simon");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.score_table_fragment, container, false);
        TextView textView = (TextView) view.findViewById(R.id.player_one_name);
        textView.setText("Simon");

        setHasOptionsMenu(true);

        ArrayList<Hole>frontNine = new ArrayList<>();

        frontNine.add(new Hole(1, 15, 3, 145));
        frontNine.add(new Hole(2, 3, 5, 449));
        frontNine.add(new Hole(3, 5, 4, 393));
        frontNine.add(new Hole(4, 13, 3, 153));
        frontNine.add(new Hole(5, 7, 4, 332));
        frontNine.add(new Hole(6, 11, 4, 291));
        frontNine.add(new Hole(7, 1, 5, 469));
        frontNine.add(new Hole(8, 17, 3, 138));
        frontNine.add(new Hole(9, 9, 4, 301));

        ArrayList<Hole>backNine = new ArrayList<>();

        backNine.add(new Hole(10, 12, 4, 338));
        backNine.add(new Hole(11, 14, 4, 313));
        backNine.add(new Hole(12, 10, 4, 356));
        backNine.add(new Hole(13, 2, 5, 486));
        backNine.add(new Hole(14, 6, 4, 386));
        backNine.add(new Hole(15, 16, 3, 129));
        backNine.add(new Hole(16, 4, 5, 467));
        backNine.add(new Hole(17, 18, 3, 113));
        backNine.add(new Hole(18, 8, 4, 326));

        //init stuff
        initScorecard(R.id.hole_view_front, view, frontNine);
        initScorecard(R.id.hole_view_back, view, backNine);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.scorecard, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //activityCommander.openScorecardSummary();
        return super.onOptionsItemSelected(item);
    }

    private void initScorecard(int id, View view, ArrayList<Hole>holes){

        rv=(RecyclerView) view.findViewById(id);

        CustomLayoutManager layoutManager = new CustomLayoutManager(view.getContext());
        layoutManager.setScrollEnabled(false);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);

        initScorecardData(holes);
    }

    private void initScorecardData(ArrayList<Hole> holes){

        simon = new Player();
        simon.setFirstName("Simon");
        simon.setHandicap(15.5);
        simon.setScores(new int[]{5, 4, 3, 5, 5, 7, 8, 6, 5,5, 4, 3, 5, 5, 7, 8, 6, 5});

        ArrayList<Player> players = new ArrayList<>();
        players.add(simon);
        //public Player(String firstName, String lastName, String id, String password, double handicap, ArrayList<String> clubs,int[] scores)

        Club harekaer = new Club();

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

        Course hk18 = new Course("Harekaer 18-Hullers", holes, 132, 70.6, hk18Metadata);

        ArrayList<Course>courses = new ArrayList<>();
        courses.add(hk18);

        harekaer.setCourses(courses);

        currentScorecard = new Scorecard();
        currentScorecard.setClub(harekaer);
        currentScorecard.setPlayers(players);

        ScorecardRecyclerAdapter adapter = new ScorecardRecyclerAdapter(currentScorecard, false);

        rv.setAdapter(adapter);
    }
}
