package com.gdz.ultramovie;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class insertMovieData extends AppCompatActivity {
    private static final String TAG = "insertMovieData";
    String username;
    Button chooseImage, insertData;
    EditText name_movie, tahun, direktor, penulis, genre, artis, sinopsis, writerID;
    ImageView posterMovie;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_movie_data);
        chooseImage = findViewById(R.id.btnChooseImage);
        insertData = findViewById(R.id.btnInsertDataMovie);
        posterMovie = findViewById(R.id.imageViewMovie);
        writerID = findViewById(R.id.idWriterEditText);
        name_movie = findViewById(R.id.movieNameEditText);
        tahun = findViewById(R.id.tahunEditText);
        direktor = findViewById(R.id.directorsEditText);
        penulis = findViewById(R.id.writerEditText);
        genre = findViewById(R.id.genreEditText);
        artis = findViewById(R.id.starsEditText);
        sinopsis = findViewById(R.id.sinopsisEditText);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            username = bundle.getString("username");
        }

        insertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieName = name_movie.getText().toString();
                String years = tahun.getText().toString();
                String directors = direktor.getText().toString();
                String idWriter = writerID.getText().toString();
                String writer = penulis.getText().toString();
                String genres = genre.getText().toString();
                String stars = artis.getText().toString();
                String synopsis = sinopsis.getText().toString();

                if (idWriter.isEmpty()&& movieName.isEmpty() && years.isEmpty() && directors.isEmpty() && writer.isEmpty() && genres.isEmpty() && stars.isEmpty() && synopsis.isEmpty()){
                    writerID.setError("This field cannot be empty!");
                    name_movie.setError("This field cannot be empty!");
                    tahun.setError("This field cannot be empty!");
                    direktor.setError("This field cannot be empty!");
                    penulis.setError("This field cannot be empty!");
                    genre.setError("This field cannot be empty!");
                    artis.setError("This field cannot be empty!");
                    sinopsis.setError("This field cannot be empty!");
                    writerID.requestFocus();
                }
                else if (idWriter.isEmpty()){
                    writerID.setError("This field cannot be empty!");
                    writerID.requestFocus();
                }
                else if (movieName.isEmpty()){
                    name_movie.setError("This field cannot be empty!");
                    name_movie.requestFocus();
                }
                else if (years.isEmpty()){
                    tahun.setError("This field cannot be empty!");
                    tahun.requestFocus();
                }
                else if (directors.isEmpty()){
                    direktor.setError("This field cannot be empty!");
                    direktor.requestFocus();
                }
                else if (writer.isEmpty()){
                    penulis.setError("This field cannot be empty!");
                    penulis.requestFocus();
                }
                else if (genres.isEmpty()){
                    genre.setError("This field cannot be empty!");
                    genre.requestFocus();
                }
                else if (stars.isEmpty()){
                    artis.setError("This field cannot be empty!");
                    artis.requestFocus();
                }
                else if (synopsis.isEmpty()){
                    sinopsis.setError("This field cannot be empty!");
                    sinopsis.requestFocus();
                }
                else {
                    uploadDataToDatabase(idWriter, movieName, years, getStringImage(bitmap), directors, writer, genres, stars, synopsis);
                }


            }
        });

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFileFromDevice();
            }
        });

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
                posterMovie.setImageBitmap(bitmap);

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

    public void uploadDataToDatabase(final String idWriter, final String movieName, final String years, final String image, final String directors, final String writer, final String genre, final String stars, final String sinopsis){
        StringRequest request = new StringRequest(Request.Method.POST, databaseURL.insertDataMovie, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("errormsg")){
                        Intent backToMainMenu = new Intent(getApplicationContext(), mainMenuAdmin.class);
                        backToMainMenu.putExtra("username", username);
                        finish();
                        startActivity(backToMainMenu);
                    }

                } catch (JSONException e) {
                    Toast.makeText(insertMovieData.this, "Error " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Toast.makeText(insertMovieData.this, "Error " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
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
}