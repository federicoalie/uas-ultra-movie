package com.gdz.ultramovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void btnSignUp(View view) {
        Intent registerActivity = new Intent(getApplicationContext(), registerActivity.class);
        startActivity(registerActivity);
    }
}