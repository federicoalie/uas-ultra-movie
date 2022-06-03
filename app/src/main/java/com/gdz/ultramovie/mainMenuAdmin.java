package com.gdz.ultramovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class mainMenuAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_admin);
    }

    public void btnAboutUs(View view) {
        Intent aboutUs = new Intent(getApplicationContext(), aboutUsActivity.class);
        startActivity(aboutUs);
    }

    public void btnProfile(View view) {
        Intent profile = new Intent(getApplicationContext(), profileActivity.class);
        startActivity(profile);
    }
}