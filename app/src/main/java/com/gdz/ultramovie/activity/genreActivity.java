package com.gdz.ultramovie.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdz.ultramovie.R;
import com.gdz.ultramovie.model.genre;
import com.gdz.ultramovie.recyclerviewadapter.genreRecyclerViewAdapter;
import com.gdz.ultramovie.databaseURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class genreActivity extends AppCompatActivity implements genreRecyclerViewAdapter.onGenreClickListener{

    private static final String TAG = "genreActivity";
    private final ArrayList<genre> genreArrayList = new ArrayList<>();
    String username, idGenre;
    Button btnAddData;
    RecyclerView gRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle!=null){
            username = bundle.getString("username");
        }

        gRecyclerView = findViewById(R.id.recyclerViewGenre);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        gRecyclerView.setLayoutManager(layoutManager);
        gRecyclerView.setHasFixedSize(true);

        btnAddData = findViewById(R.id.btnAddDataGenre);

        showGenreData();


        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent insertDataGenre = new Intent(getApplicationContext(), com.gdz.ultramovie.insertData.insertDataGenre.class);
                insertDataGenre.putExtra("username", String.valueOf(username));
                startActivity(insertDataGenre);
            }
        });

    }


    public void showGenreData(){
        StringRequest request = new StringRequest(Request.Method.POST, databaseURL.viewDataGenre, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    genreArrayList.clear();
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        genre genres = new genre();
                        genres.setId(object.getString("id_genre"));
                        genres.setGenre(object.getString("nama_genre"));
                        genreArrayList.add(genres);
                        Log.d(TAG, "Added: " + genres.getGenre());
                    }
                    setAdapterView();
                } catch (JSONException e) {
                    Toast.makeText(genreActivity.this, "Error " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(genreActivity.this, "Error " + error, Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(request);
    }

    public void hapusDataGenre(final String id){
        StringRequest request = new StringRequest(Request.Method.POST, databaseURL.deleteDataGenre, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("errormsg")){
                        Toast.makeText(genreActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                    }
                    showGenreData();
                    setAdapterView();
                } catch (JSONException e) {
                    Toast.makeText(genreActivity.this, "Error " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(genreActivity.this, "Error " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_genre", id);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    public void setAdapterView(){
        genreRecyclerViewAdapter adapter = new genreRecyclerViewAdapter(genreArrayList, this);
        gRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onGenreLongClick(genre genre, int position) {
        Log.d(TAG, "onGenreLongClick: " + genre.getId());
        Log.d(TAG, "onGenreLongClick: " + position);
        idGenre = genre.getId();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence[] pilih = {"Delete " + genre.getGenre()};
        builder.setItems(pilih, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    hapusDataGenre(idGenre);
                }
            }
        }).show();

    }


}