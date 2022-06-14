package com.gdz.ultramovie.mainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdz.ultramovie.R;
import com.gdz.ultramovie.activity.aboutUsActivity;
import com.gdz.ultramovie.activity.profileActivity;
import com.gdz.ultramovie.databaseURL;
import com.gdz.ultramovie.model.movie;
import com.gdz.ultramovie.recyclerviewadapter.movieRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class mainMenuAdmin extends AppCompatActivity {
    private static final String TAG = "mainMenuAdmin";
    FloatingActionButton fab;
    public String username;
    private final ArrayList<movie> movieArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Created Main Menu");
        setContentView(R.layout.activity_main_menu_admin);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            username = bundle.getString("username");
        }

        fab = findViewById(R.id.floatActionInsertMovie);
        mRecyclerView = findViewById(R.id.recyclerMovieAdmin);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        loadMovieList();





        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent insertMovieData = new Intent(getApplicationContext(), com.gdz.ultramovie.insertData.insertMovieData.class);
                insertMovieData.putExtra("username", String.valueOf(username));
                startActivity(insertMovieData);
            }
        });


    }

    void setUpAdapter(){
        movieRecyclerViewAdapter adapter = new movieRecyclerViewAdapter(movieArrayList, this);
        mRecyclerView.setAdapter(adapter);
    }


    public void loadMovieList(){
        StringRequest request = new StringRequest(Request.Method.POST, databaseURL.viewDataMovie, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    movieArrayList.clear();
                    for(int i = 0; i < jsonArray.length();i++ ){
                        JSONObject object = jsonArray.getJSONObject(i);
                        movie movies = new movie();
                        movies.setNamaMovie(object.getString("nama_movie"));
                        movies.setTahunMovie(object.getString("tahun"));
                        movies.setMovie_image(object.getString("image_path"));
                        movieArrayList.add(movies);
                        Log.d(TAG, "Added : "+ movies.getNamaMovie());
                    }
                    setUpAdapter();
                } catch (JSONException e) {
                    Toast.makeText(mainMenuAdmin.this, "Error " +e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mainMenuAdmin.this, "Error " +error, Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(request);
    }

    public void btnAboutUsAdmin(View view) {
        Intent aboutUs = new Intent(getApplicationContext(), aboutUsActivity.class);
        startActivity(aboutUs);
    }

    public void btnProfileAdmin(View view) {
        Intent profile = new Intent(getApplicationContext(), profileActivity.class);
        profile.putExtra("username", String.valueOf(username));
        startActivity(profile);
    }
}