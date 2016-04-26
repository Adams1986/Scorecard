package com.github.xb10.scorecard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.github.xb10.scorecard.model.Member;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {

    private TextView idView;
    private TextView passwordView;
    private ProgressBar loadBar;

    private LoginTask loginTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        LookAndFeel.setToolbarLogo(toolbar, LoginActivity.this, R.drawable.login_header_logo, getString(R.string.login_header));
        setSupportActionBar(toolbar);

        CardView loginView = (CardView) findViewById(R.id.card_view_login);
        idView = (TextView) findViewById(R.id.id_login_view);
        passwordView = (TextView) findViewById(R.id.password_login_view);
        loadBar = (ProgressBar) findViewById(R.id.login_progress);

        if (loginView != null) {

            loginView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    authenticate();
                }
            });
        }
    }

    private void authenticate() {

        if(loginTask != null){
            return;
        }

        String id = idView.getText().toString();
        String password = passwordView.getText().toString();

        Member currentMember = new Member();
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(id)) {
            Toast.makeText(LoginActivity.this, "Udfyld venligst begge felter", Toast.LENGTH_LONG).show();
        }
        else {
            currentMember.setId(idView.getText().toString());
            currentMember.setPassword(password);

            showProgress(true);
            loginTask = new LoginTask(currentMember);
            loginTask.execute();
        }
    }

    private class LoginTask extends AsyncTask<String, Void, String>{

        private Member currentMember;

        public LoginTask(Member currentMember){

            this.currentMember = currentMember;
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                //http://www.xyzws.com/javafaq/how-to-use-httpurlconnection-post-data-to-web-server/139 See there for method
                currentMember.setPassword(URLEncoder.encode(currentMember.getPassword(), "UTF-8"));
                currentMember.setId(URLEncoder.encode(currentMember.getId(), "UTF-8"));

                //convert to JSON with GSON lib
                String urlParameters = new Gson().toJson(currentMember);

                String json = ServerConnection.executePost("http://192.168.43.110/GolfAppServer/Model/Authentication.php", urlParameters);
//                String messageFromServer = ServerConnection.executePost("http://192.168.1.40/GolfAppServer/Model/Authentication.php", urlParameters);

                HashMap<String, String> dataFromServer = new Gson().fromJson(json, HashMap.class);



                if (dataFromServer.get("message") != null) {

                    String messageFromServer = dataFromServer.get("message");
                    //For some reason white space... from PHP
                    if (messageFromServer.equals("Login successful")) {

                        //New server call to get full member information
                        json = ServerConnection.executePost("http://192.168.43.110/GolfAppServer/Model/MemberInformation.php", urlParameters);
//                        String dataFromServer = ServerConnection.executePost("http://192.168.1.40/GolfAppServer/Model/MemberInformation.php", urlParameters);

                        if (json != null) {

                            currentMember = new Gson().fromJson(json, Member.class);
                        }
                    }
                    return messageFromServer;
                }

            }
            catch (UnsupportedEncodingException e) {
                //do something?
            }
            return "Ingen forbindelse til serveren. Prøv igen senere";
        }

        @Override
        protected void onPostExecute(String message) {

            loginTask = null;
            showProgress(false);

            //'Random' check of other parameter of the currentMember to check that it is set
            if (message.equals("Login successful")) {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("currentMember" ,currentMember);
                startActivity(intent);
            }
            else {

                passwordView.setError(message);
            }
        }

        @Override
        protected void onCancelled() {

            loginTask = null;
            showProgress(false);
        }
    }

    //Animation
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

            int animationTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loadBar.setVisibility(show ? View.VISIBLE : View.GONE);
            loadBar.animate().setDuration(animationTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loadBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        }
        else {

            loadBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}