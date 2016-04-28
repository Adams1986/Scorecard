package com.github.xb10.scorecard;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.github.xb10.scorecard.model.Club;
import com.github.xb10.scorecard.model.Course;
import com.github.xb10.scorecard.model.Member;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CourseChooserFragment extends Fragment {

    private ExpandableListView courseListView;
    private CardView btnCourseSearch;
    private EditText courseSearchEditText;
    private ArrayList<Club>clubs;
    private ArrayList<Club> header;
    private HashMap<String, List<Course>> courses;

    private CourseChooserListener activityCommander;
    private Member currentMember;

    private WizardActivity wizardActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_course_chooser, container, false);

        btnCourseSearch = (CardView) view.findViewById(R.id.card_view_course_search);
        courseSearchEditText = (EditText) view.findViewById(R.id.course_search);
        courseListView = (ExpandableListView) view.findViewById(R.id.courses_found_list_view);
        courseListView.setGroupIndicator(null);

        loadDefaultData(view);
        loadSearchData(view);

        return view;

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            activityCommander = (CourseChooserListener) activity;
            ((WizardActivity) activity).onSectionAttached(R.drawable.gb_stats, "Søg på baner");
        }
        catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (Build.VERSION.SDK_INT < 23) {
            if(context instanceof Activity){

                try{
                    a = (Activity) context;
                    activityCommander = (CourseChooserListener) a;

                    wizardActivity = (WizardActivity) a;
                    //TODO: remove probably
                    wizardActivity.onSectionAttached(R.drawable.gb_stats, "Søg på baner");
                }
                catch (ClassCastException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        wizardActivity.onSectionAttached(R.drawable.gb_stats, "Søg på baner");
    }

    public void setCurrentMember(Member currentMember) {

        this.currentMember = currentMember;
    }

    public interface CourseChooserListener {

        public void setClub(Club club);
    }

    private void loadDefaultData(View view){
        Toast.makeText(view.getContext(), currentMember.getClubs().get(0), Toast.LENGTH_SHORT).show();
        new CourseSearchTask(currentMember.getClubs().get(0), view).execute();
    }

    private void loadSearchData(final View view) {

        btnCourseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new CourseSearchTask(courseSearchEditText.getText().toString(), v).execute();
            }
        });

        courseSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {


                if(i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT){
                    new CourseSearchTask(courseSearchEditText.getText().toString(), view).execute();
                }
                return false;
            }
        });
    }

    private class CourseSearchTask extends AsyncTask<Void, Void, ArrayList<Club>> {

        private String searchText;
        private View view;

        public CourseSearchTask(String searchText, View view) {

            this.searchText = searchText;
            this.view = view;
        }

        @Override
        protected ArrayList<Club> doInBackground(Void... params) {


            try {
                String messageFromServer = ServerConnection.executeGet("http://192.168.43.110/GolfAppServer/Model/GetCourseList.php?search="
                        + URLEncoder.encode(searchText, "UTF-8"));

                clubs = new Gson().fromJson(messageFromServer, new TypeToken<ArrayList<Club>>(){}.getType());

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return clubs;
        }

        @Override
        protected void onPostExecute(ArrayList<Club> clubs) {
            searchForCourse(view);
        }
    }

    private void searchForCourse(View v) {

        if (!TextUtils.isEmpty(courseSearchEditText.getText().toString())){

            if (clubs.size() > 0) {

                header = new ArrayList<>();
                courses = new HashMap<>();
                //Converting to be compatible with adapter
                for(Club club : clubs){

                    header.add(club);
                    courses.put(club.getName(), club.getCourses());
                }

                CourseChooserListAdapter adapter = new CourseChooserListAdapter(
                        v.getContext(), header, courses);

                courseListView.setAdapter(adapter);

                courseListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                        Toast.makeText(v.getContext(),
                                header.get(groupPosition).getName() + " " + " Bane: " + courses.get(header.get(groupPosition).getName()).get(childPosition).getName(),
                                Toast.LENGTH_SHORT).show();

                        /*Intent intent = new Intent(v.getContext(), MainActivity.class);
                        //TODO: change intent to player chooser.
                        Course chosen = courses.get(header.get(groupPosition).getName()).get(childPosition);
                        intent.putExtra("course", chosen);
                        startActivity(intent);*/
                        Club selectedClub = header.get(groupPosition);

                        ArrayList<Course> onlySelectedCourse = new ArrayList<>();
                        Course selectedCourse = courses.get(header.get(groupPosition).getName()).get(childPosition);
                        onlySelectedCourse.add(selectedCourse);
                        selectedClub.setCourses(onlySelectedCourse);

                        //passing 'trimmed' information about the club and course chosen
                        activityCommander.setClub(selectedClub);

                        return false;
                    }
                });
            }
        }
    }
}
