package com.gdz.ultramovie.updateData;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdz.ultramovie.R;
import com.gdz.ultramovie.databaseURL;
import com.gdz.ultramovie.insertData.insertMovieData;
import com.gdz.ultramovie.mainActivity.mainMenuAdmin;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class updateDataMovie extends AppCompatActivity {
    private static final String TAG = "updateDataMovie";
    String nama_movie, username, imageString, idMovie;
    EditText IDWriter, moviename, tahunmovie, directormovie, writermovie, genremovie, starmovie, sinopsismovie;
    Button btnUpdatePhoto, btnUpdateData;
    ImageView updatePoster;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data_movie);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            username = bundle.getString("username");
            nama_movie = bundle.getString("nama_movie");
            idMovie = bundle.getString("id_movie");
        }

        IDWriter = findViewById(R.id.updateIDWriterEditText);
        moviename = findViewById(R.id.updateMovieNameEditText);
        tahunmovie = findViewById(R.id.updateTahunEditText);
        directormovie = findViewById(R.id.updateDirectorsEditText);
        writermovie = findViewById(R.id.updateWriterEditText);
        genremovie = findViewById(R.id.updateGenreEditText);
        starmovie = findViewById(R.id.updateStarsEditText);
        sinopsismovie = findViewById(R.id.updateSinopsisEditText);
        updatePoster = findViewById(R.id.imageViewUpdateMovie);
        btnUpdatePhoto = findViewById(R.id.btnUpdateImage);
        btnUpdateData = findViewById(R.id.btnUpdateDataMovie);

        fillDataToEditText(nama_movie);

        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String IdWriter = IDWriter.getText().toString();
                String name_movie = moviename.getText().toString();
                String tahun = tahunmovie.getText().toString();
                String director = directormovie.getText().toString();
                String writer = writermovie.getText().toString();
                String genre = genremovie.getText().toString();
                String star = starmovie.getText().toString();
                String sinopsis = sinopsismovie.getText().toString();

                updateDataMovieToDatabase(idMovie ,IdWriter, name_movie, tahun, getStringImage(bitmap), director, writer, genre, star, sinopsis);

            }
        });

        btnUpdatePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFileFromDevice();
            }
        });

    }

    public void fillDataToEditText(final String nama_movie){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, databaseURL.viewAllDataMovie, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        IDWriter.setText(object.getString("id_writer"));
                        moviename.setText(object.getString("nama_movie"));
                        tahunmovie.setText(object.getString("tahun"));
                        directormovie.setText(object.getString("directors"));
                        writermovie.setText(object.getString("writer"));
                        genremovie.setText(object.getString("genre"));
                        starmovie.setText(object.getString("stars"));
                        sinopsismovie.setText(object.getString("sinopsis"));
                        imageString = object.getString("image_path");
                        Picasso.get().load(imageString).into(updatePoster);
                    }
                } catch (JSONException e) {
                    Toast.makeText(updateDataMovie.this, "Error " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(updateDataMovie.this, "Error " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama_movie", nama_movie);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);

    }

    public void updateDataMovieToDatabase (final String idmovie, final String idWriter, final String movieName, final String years, final String image, final String directors, final String writer, final String genre, final String stars, final String sinopsis){
        StringRequest request = new StringRequest(Request.Method.POST, databaseURL.updateDataMovie, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("errormsg")){
                        Log.d(TAG, "SUCCESS");
                        Toast.makeText(updateDataMovie.this, "Movie " + movieName + " Updated Successfuly!", Toast.LENGTH_SHORT).show();
                        Intent toMainMenu = new Intent(getApplicationContext(), mainMenuAdmin.class);
                        toMainMenu.putExtra("username", String.valueOf(username));
                        finish();
                        startActivity(toMainMenu);
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_movie", idmovie);
                params.put("id_writer", idWriter);
                params.put("nama_movie", movieName);
                params.put("tahun", years);
                params.put("image_path", image);
                params.put("directors", directors);
                params.put("writer", writer);
                params.put("genre", genre);
                params.put("stars", stars);
                params.put("sinopsis", sinopsis);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    public void chooseFileFromDevice(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Your Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                updatePoster.setImageBitmap(bitmap);

            } catch (IOException e) {
                Log.d(TAG, "onActivityResult: " + e);
                e.printStackTrace();
            }

        }
    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte [] imgByteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByteArray, Base64.DEFAULT);
    }


}