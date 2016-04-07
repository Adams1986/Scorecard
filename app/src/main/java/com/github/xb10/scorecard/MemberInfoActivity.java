package com.github.xb10.scorecard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.github.xb10.scorecard.model.Member;


public class MemberInfoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Member currentMember;
    private TextView nameView;
    private TextView addressView;
    private TextView handicapView;
    private ListView clubsView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_member_info);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nameView = (TextView) findViewById(R.id.name_view);
        addressView = (TextView) findViewById(R.id.address_view);
        handicapView = (TextView) findViewById(R.id.handicap_view);
        clubsView = (ListView) findViewById(R.id.clubs_view);


        LookAndFeel.setToolbarLogo(toolbar, MemberInfoActivity.this, R.drawable.member_information, getString(R.string.member_info_header));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initMemberInformation();
    }

    private void initMemberInformation() {

        currentMember = (Member) getIntent().getSerializableExtra("currentMember");

        nameView.setText(currentMember.getFullName());
        addressView.setText(currentMember.getAddress());
        handicapView.setText(String.format("%.1f", currentMember.getHandicap()));

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.content_clubs_list_view, currentMember.getClubs());
        clubsView.setAdapter(adapter);
    }
}
