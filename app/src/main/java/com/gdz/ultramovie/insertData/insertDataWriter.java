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
import com.gdz.ultramovie.activity.writerActivity;
import com.gdz.ultramovie.databaseURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class insertDataWriter extends AppCompatActivity {
    private static final String TAG = "insertDataWriter";
    String username;
    EditText writer, emailWriter, telephoneNum;
    Button btnAddDataWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data_writer);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            username = bundle.getString("username");
        }

        writer = findViewById(R.id.insertWriterEditText);
        emailWriter = findViewById(R.id.insertEmailEditText);
        telephoneNum = findViewById(R.id.insertTelephoneEditText);
        btnAddDataWriter = findViewById(R.id.btnInsertDataWriter);

        btnAddDataWriter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaWriter = writer.getText().toString();
                String email = emailWriter.getText().toString();
                String telephone = telephoneNum.getText().toString();

                if (namaWriter.isEmpty() && email.isEmpty() && telephone.isEmpty()){
                    writer.setError("This field cannot be empty!");
                    emailWriter.setError("This field cannot be empty!");
                    telephoneNum.setError("This field cannot be empty!");
                    writer.requestFocus();
                }
                else if (namaWriter.isEmpty()){
                    writer.setError("This field cannot be empty!");
                    writer.requestFocus();
                }
                else if (email.isEmpty()){
                    emailWriter.setError("This field cannot be empty!");
                    emailWriter.requestFocus();
                }
                else if (telephone.isEmpty()){
                    telephoneNum.setError("This field cannot be empty!");
                    telephoneNum.requestFocus();
                }
                else {
                    addDataWriter(namaWriter, email, telephone);
                }
            }
        });

    }

    public void addDataWriter(final String writer, final String email, final String telephone){
        StringRequest request = new StringRequest(Request.Method.POST, databaseURL.insertDataWriter, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("errormsg")){
                        Toast.makeText(insertDataWriter.this, "Data Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), writerActivity.class);
                        intent.putExtra("username", username);
                        finish();
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    Toast.makeText(insertDataWriter.this, "Error " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(insertDataWriter.this, "Error " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama_writer", writer);
                params.put("email", email);
                params.put("telepon", telephone);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

}