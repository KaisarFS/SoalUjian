package com.example.newprojectonlinelearning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import org.w3c.dom.Text;

public class DetailFavourite extends AppCompatActivity {

    Bundle extras;
    String id, title, description, date, path, gender, country;

    TextView judul, desc, countryFav, yearFav, genderFav;
    ImageView ivposter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_favourite);
        judul = (TextView) findViewById(R.id.teamNAmeDetailFav);
        desc = (TextView) findViewById(R.id.teamDescFav);
        countryFav = (TextView) findViewById(R.id.teamCountryDetailFav);
        yearFav = (TextView) findViewById(R.id.formedYearFav);
        genderFav = (TextView) findViewById(R.id.teamGenderDetailFav);
        ivposter = (ImageView) findViewById(R.id.teamBadgesDetailFav);
        extras = getIntent().getExtras();
        desc.setMovementMethod(new ScrollingMovementMethod());


        if (extras != null) {
            title = extras.getString("name");
            id = extras.getString("id");
            description = extras.getString("desc");
            path = extras.getString("badge");
            date = extras.getString("year");
            gender = extras.getString("alternate");
            country = extras.getString("country");

            judul.setText(title);
            desc.setText(description);
            countryFav.setText(country);
            genderFav.setText(gender);
            yearFav.setText(date);

            Glide.with(DetailFavourite.this)
                    .load(path)
                    .override(Target.SIZE_ORIGINAL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(ivposter);
            // and get whatever type user account id is
        }
    }
}