package com.gdz.ultramovie.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.gdz.ultramovie.databaseURL;
import com.gdz.ultramovie.mainActivity.mainMenuAdmin;
import com.gdz.ultramovie.mainActivity.mainMenuMember;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class loginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText usernameLogin, passwordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnSignIn);
        usernameLogin = findViewById(R.id.usernameEditText);
        passwordLogin = findViewById(R.id.passwordEditText);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = usernameLogin.getText().toString();
                String pass = passwordLogin.getText().toString();

                if(user.isEmpty() && pass.isEmpty()){
                    usernameLogin.setError("Username cannot be empty!");
                    passwordLogin.setError("Password cannot be empty!");
                    usernameLogin.requestFocus();
                }
                else if (user.isEmpty()){
                    usernameLogin.setError("Username cannot be empty!");
                    usernameLogin.requestFocus();
                }
                else if(pass.isEmpty()){
                    passwordLogin.setError("Password cannot be empty");
                    passwordLogin.requestFocus();
                }
                else {
                    checkUserValidity(user, pass);
                }

            }
        });

    }

    public void checkUserValidity(final String username, final String password){
        StringRequest request = new StringRequest(Request.Method.POST, databaseURL.login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String level = jsonObject.getString("level");
                    if(level.equals("Admin")){
                        Toast.makeText(loginActivity.this, "Selamat Datang " + username, Toast.LENGTH_SHORT).show();
                        Intent adminHome = new Intent(getApplicationContext(), mainMenuAdmin.class);
                        adminHome.putExtra("username", username);
                        startActivity(adminHome);
                        finish();
                    }
                    else {
                        Toast.makeText(loginActivity.this, "Selamat Datang " + username, Toast.LENGTH_SHORT).show();
                        Intent memberHome = new Intent(getApplicationContext(), mainMenuMember.class);
                        memberHome.putExtra("username", username);
                        startActivity(memberHome);
                        finish();
                    }
                } catch (JSONException e) {
                    usernameLogin.setError("Username or Password incorrect!");
                    passwordLogin.setError("Username or Password incorrect!");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loginActivity.this, "Error " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("userPassword", password);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    public void btnSignUp(View view) {
        Intent registerActivity = new Intent(getApplicationContext(), com.gdz.ultramovie.activity.registerActivity.class);
        startActivity(registerActivity);

    }
}