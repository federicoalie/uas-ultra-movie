package com.gdz.ultramovie.insertData;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.gdz.ultramovie.R;

public class insertDataWriter extends AppCompatActivity {

    String username;
    EditText getIDGenre, getGenre;
    Button btnAddDataGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data_writer);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){

        }

    }

}