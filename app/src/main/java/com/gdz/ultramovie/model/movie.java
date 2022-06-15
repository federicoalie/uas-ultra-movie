package com.gdz.ultramovie.model;

import android.os.Parcel;
import android.os.Parcelable;

public class movie {
    String namaMovie;
    String tahunMovie;
    String movie_image;
    String idMovie;


    public String getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(String idMovie) {
        this.idMovie = idMovie;
    }


    public String getNamaMovie() {
        return namaMovie;
    }

    public void setNamaMovie(String namaMovie) {
        this.namaMovie = namaMovie;
    }

    public String getTahunMovie() {
        return tahunMovie;
    }

    public void setTahunMovie(String tahunMovie) {
        this.tahunMovie = tahunMovie;
    }

    public String getMovie_image() {
        return movie_image;
    }

    public void setMovie_image(String movie_image) {
        this.movie_image = movie_image;
    }

}
