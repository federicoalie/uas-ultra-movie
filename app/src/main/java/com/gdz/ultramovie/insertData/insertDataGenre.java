package com.gdz.ultramovie.insertData;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.gdz.ultramovie.activity.genreActivity;
import com.gdz.ultramovie.databaseURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class insertDataGenre extends AppCompatActivity {

    private static final String TAG = "insertDataGenre";
    String username;
    EditText id, namaGenre;
    Button btnAddData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data_genre);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            username = bundle.getString("username");
        }

        id = findViewById(R.id.insertIDGenreEditText);
        namaGenre = findViewById(R.id.insertGenreEditText);
        btnAddData = findViewById(R.id.btnInsertDataGenre);

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idGenre = id.getText().toString();
                String genre = namaGenre.getText().toString();

                if (idGenre.isEmpty() && genre.isEmpty()){
                    id.setError("This field cannot be empty!");
                    namaGenre.setError("This field cannot be empty!");
                    id.requestFocus();
                }
                else if (idGenre.isEmpty()){
                    id.setError("This field cannot be empty!");
                    id.requestFocus();
                }
                else if (genre.isEmpty()){
                    namaGenre.setError("This field cannot be empty!");
                    namaGenre.requestFocus();
                }
                else {
                    addDataToDatabase(idGenre, genre);
                }
            }
        });


    }

    public void addDataToDatabase (final String id, final String genre){
        StringRequest request = new StringRequest(Request.Method.POST, databaseURL.insertDataGenre, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("errormsg")){
                        Toast.makeText(insertDataGenre.this, "Genre Added", Toast.LENGTH_SHORT).show();
                        Intent back = new Intent(getApplicationContext(), genreActivity.class);
                        back.putExtra("username", String.valueOf(username));
                        finish();
                        startActivity(back);
                    }
                } catch (JSONException e) {
                    Toast.makeText(insertDataGenre.this, "Error " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(insertDataGenre.this, "Error " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_genre", id);
                params.put("nama_genre", genre);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}