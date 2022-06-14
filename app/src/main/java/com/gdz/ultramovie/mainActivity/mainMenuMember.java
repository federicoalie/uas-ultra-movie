package com.gdz.ultramovie.mainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gdz.ultramovie.R;
import com.gdz.ultramovie.activity.aboutUsActivity;
import com.gdz.ultramovie.activity.profileActivity;

public class mainMenuMember extends AppCompatActivity {

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_member);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            username = bundle.getString("username");
        }
    }

    public void btnProfile(View view) {
        Intent profile = new Intent(getApplicationContext(), profileActivity.class);
        profile.putExtra("username", String.valueOf(username));
        startActivity(profile);
    }

    public void btnAboutUs(View view) {
        Intent aboutUs = new Intent(getApplicationContext(), aboutUsActivity.class);
        startActivity(aboutUs);
    }
}