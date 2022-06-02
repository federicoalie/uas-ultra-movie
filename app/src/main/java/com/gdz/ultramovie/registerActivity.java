package com.gdz.ultramovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class registerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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