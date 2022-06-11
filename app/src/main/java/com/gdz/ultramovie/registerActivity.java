package com.gdz.ultramovie;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class registerActivity extends AppCompatActivity {

    Button btnSignIn;
    EditText fullName, username, country, password, confirmPassword;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnSignIn = findViewById(R.id.btnSignIn);
        fullName = findViewById(R.id.editTextFullName);
        username = findViewById(R.id.editTextUsername);
        country = findViewById(R.id.editTextCountry);
        password = findViewById(R.id.editTextPassword);
        confirmPassword = findViewById(R.id.editTextConfirmPassword);
        radioGroup = findViewById(R.id.groupRadio);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedItems = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedItems);
                String name = fullName.getText().toString();
                String user = username.getText().toString();
                String jenisKelamin = radioButton.getText().toString();
                String negara = country.getText().toString();
                String pass = password.getText().toString();
                String confPass = confirmPassword.getText().toString();
                String level = "Member";

                if(name.isEmpty() && user.isEmpty() && negara.isEmpty() && pass.isEmpty() && confPass.isEmpty()){
                    fullName.setError("This Field Cannot be Empty!");
                    username.setError("This Field Cannot be Empty!");
                    country.setError("This Field Cannot be Empty!");
                    password.setError("This Field Cannot be Empty!");
                    confirmPassword.setError("This Field Cannot be Empty!");
                    fullName.requestFocus();
                }
                else if(name.isEmpty()){
                    fullName.setError("This Field Cannot be Empty!");
                    fullName.requestFocus();
                }
                else if(user.isEmpty()){
                    username.setError("This Field Cannot be Empty!");
                }
                else if(negara.isEmpty()){
                    country.setError("This Field Cannot be Empty!");
                }
                else if(pass.isEmpty()){
                    password.setError("This Field Cannot be Empty!");
                }
                else if(confPass.isEmpty()){
                    confirmPassword.setError("This Field Cannot be Empty!");
                }
                else if(pass.length() <= 8){
                    password.setError("Password must have at least 8 character or more!");
                }
                else if(!pass.equals(confPass)){
                    confirmPassword.setError("Password not match!");
                }
                else {
                    registerToDatabase(user, name, jenisKelamin, negara, confPass, level);
                }

            }
        });


    }

    public void registerToDatabase(String username, String fullName, String gender, String country, String userPassword, String userLevel){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, databaseURL.register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(!jsonObject.getBoolean("errormsg")){
                        Toast.makeText(registerActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(getApplicationContext(), loginActivity.class);
                        startActivity(login);
                        finish();
                    }
                } catch (JSONException e) {
                    Toast.makeText(registerActivity.this, "Error " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(registerActivity.this, "Error " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("nama", fullName);
                params.put("jenis_kelamin", gender);
                params.put("asal_negara", country);
                params.put("userPassword", userPassword);
                params.put("userLevel", userLevel);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }


    public void btnBack(View view) {
        Intent backToLogin = new Intent(getApplicationContext(), loginActivity.class);
        startActivity(backToLogin);
        finish();
    }

    public void btnSignIn(View view) {
        Intent loginPage = new Intent(getApplicationContext(), loginActivity.class);
        startActivity(loginPage);
        finish();
    }
}