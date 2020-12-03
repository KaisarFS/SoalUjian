package com.example.newprojectonlinelearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import android.app.ProgressDialog;

public class  ListData extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DataAdapter adapter;
    private ArrayList<Model> DataArrayList; //kit add kan ke adapter
    private ImageView tambah_data;
    TextView tvnodata;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);
        recyclerView = (RecyclerView) findViewById(R.id.rvdata);
        tvnodata = (TextView) findViewById(R.id.tvnodata);
        dialog = new ProgressDialog(ListData.this);
        tvnodata.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        //addData();
        addDataOnline();
    }

    void addDataOnline(){
        //kasih loading
        dialog.setMessage("Sedang memproses data");
        dialog.show();
        // background process
        AndroidNetworking.get("https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?l=English%20Premier%20League")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.d("hasiljson", "onResponse: " + response.toString());
                        //jika sudah berhasil debugm lanjutkan code dibawah ini
                        DataArrayList = new ArrayList<>();
                        Model modelku;
                        try {
                            Log.d("hasiljson", "onResponse: " + response.toString());
                            JSONArray jsonArray = response.getJSONArray("teams");
                            Log.d("hasiljson2", "onResponse: " + jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                modelku = new Model();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                modelku.setIdTeam(jsonObject.getInt("idTeam"));
                                modelku.setStrTeam(jsonObject.getString("strTeam"));
                                modelku.setStrTeamBadge(jsonObject.getString("strTeamBadge"));
                                modelku.setStrCountry(jsonObject.getString("strCountry"));
                                modelku.setStrDescriptionEN(jsonObject.getString("strDescriptionEN"));
                                modelku.setStrStadiumThumb(jsonObject.getString("strStadiumThumb"));
                                modelku.setIntFormedYear(jsonObject.getString("intFormedYear"));
                                modelku.setStrStadiumLocation(jsonObject.getString("strStadiumLocation"));
                                modelku.setStrStadium(jsonObject.getString("strStadium"));
                                modelku.setStrStadiumDescription(jsonObject.getString("strStadiumDescription"));
                                DataArrayList.add(modelku);
                            }
                            //untuk handle click
                            adapter = new DataAdapter(DataArrayList, new DataAdapter.Callback() {
                                @Override
                                public void onClick(int position) {
                                    Model myClub = DataArrayList.get(position);
                                    Intent intent = new Intent(getApplicationContext(), DetailClub.class);
                                    intent.putExtra("idTeam",myClub.idTeam);
                                    intent.putExtra("namaClub",myClub.strTeam);
                                    intent.putExtra("logoClub",myClub.strTeamBadge);
                                    intent.putExtra("deskripsiClub",myClub.strDescriptionEN);
                                    intent.putExtra("formedYear",myClub.intFormedYear);
                                    intent.putExtra("country",myClub.strCountry);
//                                    intent.putExtra("namaStadium",myClub.teamStadium);
//                                    intent.putExtra("lokasiStadium",myClub.teamStadiumLocation);
//                                    intent.putExtra("deskripsiStadium",myClub.teamStadiumDescription);
                                    startActivity(intent);
                                    Toast.makeText(ListData.this, ""+position, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void test() {

                                }
                            });
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListData.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("errorku", "onError errorCode : " + error.getErrorCode());
                        Log.d("errorku", "onError errorBody : " + error.getErrorBody());
                        Log.d("errorku", "onError errorDetail : " + error.getErrorDetail());
                    }
                });
    }
}