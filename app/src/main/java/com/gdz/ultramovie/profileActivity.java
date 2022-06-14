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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class profileActivity extends AppCompatActivity {

    private static final String TAG = "profileActivity";
    CircleImageView profileImage;
    Button btnSignOut;
    String username, prfImage;
    private Bitmap bitmap;
    TextView setFullName, setUsername, setGender, setCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage = findViewById(R.id.circlePhotoProfile);
        setFullName = findViewById(R.id.getFullName);
        setUsername = findViewById(R.id.getUsername);
        setGender = findViewById(R.id.getGender);
        setCountry = findViewById(R.id.getCountry);
        btnSignOut = findViewById(R.id.btnSignOut);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            username = bundle.getString("username");
        }


        putAllUserData(username);

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signOut = new Intent(getApplicationContext(), loginActivity.class);
                startActivity(signOut);
                finish();
            }
        });

    }

    public void btnEditPhoto(View view) {
        chooseFileFromDevice();
    }

    public void chooseFileFromDevice(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Your Picture"), 1);

    }

    public void uploadProfilePictureToDatabase(String username, String photo){
        StringRequest request = new StringRequest(Request.Method.POST, databaseURL.uploadProfileImage, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("TAG", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(!jsonObject.getBoolean("errormsg")){
                        Toast.makeText(profileActivity.this, "Upload Succeed!", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    Toast.makeText(profileActivity.this, "Error " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(profileActivity.this, "Error " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("image_path", photo);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profileImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

            uploadProfilePictureToDatabase(username, getStringImage(bitmap));

        }
    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte [] imgByteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByteArray, Base64.DEFAULT);
    }

    public void putAllUserData(final String username){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, databaseURL.viewUserData, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for(int i = 0; i < jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        setUsername.setText(username);
                        setFullName.setText(object.getString("nama"));
                        setCountry.setText(object.getString("asal_negara"));
                        setGender.setText(object.getString("jenis_kelamin"));
                        prfImage = object.getString("image_path");

                        if(prfImage.isEmpty()){
                            Log.d(TAG, "onResponse: " + response);
                        }
                        else {
                            Picasso.get().load(prfImage).into(profileImage);
                        }

                    }

                } catch (JSONException e) {
                    Toast.makeText(profileActivity.this, "Error " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(profileActivity.this, "Error " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

}