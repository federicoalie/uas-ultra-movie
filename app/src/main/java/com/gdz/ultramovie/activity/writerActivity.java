package com.gdz.ultramovie.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdz.ultramovie.R;
import com.gdz.ultramovie.databaseURL;
import com.gdz.ultramovie.model.writer;
import com.gdz.ultramovie.recyclerviewadapter.writerRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class writerActivity extends AppCompatActivity implements writerRecyclerViewAdapter.onWriterClickListener{
    private static final String TAG = "writerActivity";
    private final ArrayList<writer> writerArrayList = new ArrayList<>();
    String username, id;
    RecyclerView wRecyclerView;
    Button btnAddDataWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writer);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            username = bundle.getString("username");
        }

        btnAddDataWriter = findViewById(R.id.btnAddDataWriter);
        wRecyclerView = findViewById(R.id.recyclerViewWriter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        wRecyclerView.setLayoutManager(layoutManager);
        wRecyclerView.setHasFixedSize(true);

        showDataWriter();

        btnAddDataWriter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addWriter = new Intent(getApplicationContext(), com.gdz.ultramovie.insertData.insertDataWriter.class);
                addWriter.putExtra("username", String.valueOf(username));
                startActivity(addWriter);
            }
        });
    }

    public void showDataWriter(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, databaseURL.viewDataWriter, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    writerArrayList.clear();
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        writer writers = new writer();
                        writers.setId(object.getString("id_writer"));
                        writers.setWriter(object.getString("nama_writer"));
                        writers.setEmail(object.getString("email"));
                        writers.setNotel(object.getString("telepon"));
                        writerArrayList.add(writers);
                        Log.d(TAG, "Added: " + writers.getWriter());
                    }
                    setUpAdapter();
                } catch (JSONException e) {
                    Toast.makeText(writerActivity.this, "Error " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Toast.makeText(writerActivity.this, "Error " + error, Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void hapusDataWriter(String idWriter){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, databaseURL.deleteDataWriter, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("errormsg")){
                        Toast.makeText(writerActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                    }
                    showDataWriter();
                    setUpAdapter();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_writer", idWriter);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void setUpAdapter(){
        writerRecyclerViewAdapter adapter = new writerRecyclerViewAdapter(writerArrayList, this);
        wRecyclerView.setAdapter(adapter);
    }


    @Override
    public void onWriterLongClick(writer writer, int position) {
        Log.d(TAG, "onWriterLongClick: " + writer.getWriter());
        Log.d(TAG, "onWriterLongClick: " + position);
        id = writer.getId();
        CharSequence[] action = {"Delete " + writer.getWriter()};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(action, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                    hapusDataWriter(id);
                }
            }
        }).show();
    }
}