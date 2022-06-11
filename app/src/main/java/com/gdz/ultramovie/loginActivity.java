package com.gdz.ultramovie;

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

import org.json.JSONArray;
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

                checkValidity(user, pass);
            }
        });

    }

    public void checkValidity(final String username, final String password){
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
                    Toast.makeText(loginActivity.this, "Error " + e, Toast.LENGTH_SHORT).show();
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
        Intent registerActivity = new Intent(getApplicationContext(), registerActivity.class);
        startActivity(registerActivity);
    }
}