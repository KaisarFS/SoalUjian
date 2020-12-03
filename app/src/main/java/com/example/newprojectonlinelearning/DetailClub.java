package com.example.newprojectonlinelearning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DetailClub extends AppCompatActivity {

    Realm realm;
    RealmHelper realmHelper;
    ModelFootballRealm clubModel;


    Bundle extras;
    String title;
    String gender;
    String country;
    String date;
    String description;
    String path;
    String id;

    ImageView teamBadges;
    TextView teamDesc, teamName, teamGender, teamYear, teamCountry;
    Button bBookmark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_club);
        extras = getIntent().getExtras();
        teamName = (TextView)findViewById(R.id.teamNameDetail);
        teamDesc = (TextView)findViewById(R.id.teamDesc);
        teamBadges = (ImageView) findViewById(R.id.teamBadgesDetail);
        teamGender = (TextView) findViewById(R.id.teamGender);
        teamYear = (TextView) findViewById(R.id.formedYear);
        teamCountry = (TextView) findViewById(R.id.teamCountryDetail);
        bBookmark = (Button) findViewById(R.id.bBookmark);
        teamDesc.setMovementMethod(new ScrollingMovementMethod());

        if (extras != null) {
            title = extras.getString("namaClub");
            id = extras.getString("idTeam");
            description = extras.getString("deskripsiClub");
            path = extras.getString("logoClub");
            date = extras.getString("formedYear");
            gender = extras.getString("alternateTeamName");
            country = extras.getString("country");

            teamCountry.setText(country);
            teamGender.setText(gender);
            teamYear.setText(date);
            teamName.setText(title);
            teamDesc.setText(description);

            Glide.with(DetailClub.this)
                    .load(path)
                    .override(Target.SIZE_ORIGINAL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(teamBadges);
            // and get whatever type user account id is
        }

        //Set up Realm
        Realm.init(DetailClub.this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);


        bBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clubModel = new ModelFootballRealm();
                clubModel.setDesc(description);
                clubModel.setGender(gender);
                clubModel.setJudul(title);
                clubModel.setPath(path);
                clubModel.setYear(date);
                clubModel.setCountry(country);

                realmHelper = new RealmHelper(realm);
                realmHelper.save(clubModel);
            }
        });

    }
}