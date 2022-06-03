package com.gdz.ultramovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class loginActivity extends AppCompatActivity {

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnSignIn);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenuAdmin = new Intent(getApplicationContext(), com.gdz.ultramovie.mainMenuAdmin.class);
                startActivity(mainMenuAdmin);
                finish();
            }
        });

    }

    public void btnSignUp(View view) {
        Intent registerActivity = new Intent(getApplicationContext(), registerActivity.class);
        startActivity(registerActivity);
    }
}