package com.gdz.ultramovie.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdz.ultramovie.R;
import com.gdz.ultramovie.databaseURL;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class movieDetailActivity extends AppCompatActivity {
    private static final String TAG = "movieDetailActivity";
    String username, movieName, mvImage;
    TextView setJudulDetailMovie, setGenre, setWriter, setStar, setSinopsis, setDirector;
    ImageView setImageDetailMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        setJudulDetailMovie = findViewById(R.id.getJudulMovieDetail);
        setGenre = findViewById(R.id.getGenre);
        setWriter = findViewById(R.id.getWriter);
        setStar = findViewById(R.id.getStars);
        setSinopsis = findViewById(R.id.getSinopsis);
        setDirector = findViewById(R.id.getDirectors);
        setImageDetailMovie = findViewById(R.id.getImageViewMovieDetail);
        setSinopsis.setMovementMethod(new ScrollingMovementMethod());
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            username = bundle.getString("username");
            movieName = bundle.getString("movie_name");
        }

        showAllMovieData();

    }



    public void showAllMovieData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, databaseURL.viewAllDataMovie, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d(TAG, "onResponse: " + response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        setJudulDetailMovie.setText(movieName);
                        setGenre.setText(object.getString("genre"));
                        setDirector.setText(object.getString("directors"));
                        setWriter.setText(object.getString("writer"));
                        setStar.setText(object.getString("stars"));
                        setSinopsis.setText(object.getString("sinopsis"));
                        mvImage = object.getString("image_path");

                        Picasso.get().load(mvImage).into(setImageDetailMovie);

                    }
                } catch (JSONException e) {
                    Toast.makeText(movieDetailActivity.this, "Error " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(movieDetailActivity.this, "Error " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama_movie", movieName);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

}