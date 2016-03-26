package com.github.xb10.scorecard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);

        CardView loginView = (CardView) findViewById(R.id.card_view_login);

        if (loginView != null) {

            loginView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //TODO Login method
                }
            });
        }
    }
}
