package com.gdz.ultramovie;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Connection {

    private static Connection koneksi;
    private RequestQueue requestQueue;
    private final Context konteks;

    private Connection(Context context){
        konteks = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(konteks.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized Connection getInstance(Context context){
        if(koneksi == null){
            koneksi = new Connection(context.getApplicationContext());
        }
        return koneksi;
    }

    public <T> void addToRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }

}
