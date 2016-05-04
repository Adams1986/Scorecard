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
import com.github.xb10.scorecard.model.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by ADI on 04-05-2016.
 */
public class PlayerSearchFragment extends Fragment {

    public static final String CURRENT_PLAYERS_KEY = "CurrentPlayers";
    private ListView playerListView;
    private CardView btnPlayerSearch;
    private EditText playerSearchEditText;
    private ArrayList<Player> players;
    private ArrayList<Player> currentPlayers;

    private PlayerSearchListener activityCommander;

    private WizardActivity wizardActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_player_search, container, false);

        getArgs();

        btnPlayerSearch = (CardView) view.findViewById(R.id.card_view_player_search);
        playerSearchEditText = (EditText) view.findViewById(R.id.player_search);
        playerListView = (ListView) view.findViewById(R.id.players_found_list_view);

        loadSearchData(view);

        return view;

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            activityCommander = (PlayerSearchListener) activity;
            ((WizardActivity) activity).onSectionAttached(R.drawable.plus, "Tilføj spiller");
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (Build.VERSION.SDK_INT < 23) {
            if (context instanceof Activity) {

                try {
                    a = (Activity) context;
                    activityCommander = (PlayerSearchListener) a;

                    wizardActivity = (WizardActivity) a;
                    wizardActivity.onSectionAttached(R.drawable.plus, "Tilføj spiller");
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        wizardActivity.onSectionAttached(R.drawable.plus, "Tilføj spiller");
    }

    public interface PlayerSearchListener {

        public void setPlayers(ArrayList<Player> players);
    }

    private void loadSearchData(final View view) {

        btnPlayerSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PlayerSearchTask(playerSearchEditText.getText().toString(), v).execute();

                if (playerSearchEditText.hasFocus()) {
                    LookAndFeel.hideSoftKeyboard(v.getContext());
                }

            }
        });

        playerSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {


                if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH) {
                    new PlayerSearchTask(playerSearchEditText.getText().toString(), view).execute();

                    if (playerSearchEditText.hasFocus()) {
                        LookAndFeel.hideSoftKeyboard(view.getContext());
                    }
                }
                return false;
            }
        });
    }

    private class PlayerSearchTask extends AsyncTask<Void, Void, ArrayList<Player>> {

        private String searchText;
        private View view;

        public PlayerSearchTask(String searchText, View view) {

            this.searchText = searchText;
            this.view = view;
        }

        @Override
        protected ArrayList<Player> doInBackground(Void... params) {


            try {
                String messageFromServer = ServerConnection.executeGet("http://" + getActivity().getString(R.string.server_url) + "/Model/GetMembers.php?search="
                        + URLEncoder.encode(searchText, "UTF-8"));

                players = new Gson().fromJson(messageFromServer, new TypeToken<ArrayList<Player>>() {
                }.getType());

            } catch (UnsupportedEncodingException | NullPointerException e) {
                e.printStackTrace();
            }

            return players;
        }

        @Override
        protected void onPostExecute(ArrayList<Player> players) {
            searchForPlayers(view);
        }
    }

    private void getArgs() {
        //Getting arguments set from associated activity
        Bundle args = getArguments();

        //If args has value, set textfield. Don't use key! if no args nl
        if (args != null) {

            currentPlayers = ((ArrayList<Player>) args.getSerializable(CURRENT_PLAYERS_KEY));
        }
    }

    private void searchForPlayers(View v) {

        if (!TextUtils.isEmpty(playerSearchEditText.getText().toString())) {

            if (players != null) {
                if (players.size() > 0) {

                    PlayerSearchListAdapter adapter = new PlayerSearchListAdapter(
                            v.getContext(), players);

                    playerListView.setAdapter(adapter);

                    playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            boolean addToList = true;

                            /*for (Player temp : currentPlayers){

                                if (!temp.getId().equals(players.get(position).getId())) {

                                    addToList = true;
                                }
                            }*/
                            if (addToList){
//                                Toast.makeText(view.getContext(), "Spiller tilføjes", Toast.LENGTH_SHORT).show();
                                currentPlayers.add(currentPlayers.size() - 1, players.get(position));
                            }
                            if(currentPlayers.size() > 4){
                                currentPlayers.remove(currentPlayers.size()-1);
                            }
                            activityCommander.setPlayers(currentPlayers);
                            getFragmentManager().popBackStack();
                        }
                    });
                }
            }
        }
    }
}
