package com.gdz.ultramovie.mainActivity;

import androidx.appcompat.app.AppCompatActivity;
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
import com.gdz.ultramovie.recyclerviewadapter.movieRecyclerViewAdapterMember;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class mainMenuMember extends AppCompatActivity implements movieRecyclerViewAdapterMember.onMovieMemberClickListener {
    private static final String TAG = "mainMenuMember";
    String username;
    private final ArrayList<movie> movieArrayList = new ArrayList<>();
    RecyclerView mmRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_member);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            username = bundle.getString("username");
        }

        mmRecyclerView = findViewById(R.id.recyclerMovieMember);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mmRecyclerView.setLayoutManager(layoutManager);
        mmRecyclerView.setHasFixedSize(true);

        loadMovieData();

    }

    public void loadMovieData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, databaseURL.viewDataMovie, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    movieArrayList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        movie movies = new movie();
                        movies.setNamaMovie(object.getString("nama_movie"));
                        movies.setTahunMovie(object.getString("tahun"));
                        movies.setMovie_image(object.getString("image_path"));
                        movies.setIdMovie(object.getString("id_movie"));
                        movieArrayList.add(movies);
                        Log.d(TAG, "Added : " + movies.getNamaMovie());
                    }
                    setAdapter();
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mainMenuMember.this, "Error " + error, Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void setAdapter(){
        movieRecyclerViewAdapterMember adapterMember = new movieRecyclerViewAdapterMember(movieArrayList, this);
        mmRecyclerView.setAdapter(adapterMember);
    }

    public void btnProfile(View view) {
        Intent profile = new Intent(getApplicationContext(), profileActivity.class);
        profile.putExtra("username", String.valueOf(username));
        startActivity(profile);
    }

    public void btnAboutUs(View view) {
        Intent aboutUs = new Intent(getApplicationContext(), aboutUsActivity.class);
        startActivity(aboutUs);
    }

    @Override
    public void onMemberClick(movie movie, int position) {
        Log.d(TAG, "onMemberClick: " + movie.getNamaMovie());
        Log.d(TAG, "onMemberClick: " + position);
        Intent toMovieDetail = new Intent(getApplicationContext(), com.gdz.ultramovie.activity.movieDetailActivity.class);
        toMovieDetail.putExtra("movie_name", movie.getNamaMovie());
        startActivity(toMovieDetail);
    }
}