package com.example.sanyo.a801020745_midterm;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sanyo on 3/12/2018.
 */

public class App implements Serializable {
    String name, releaseDate, artistname, url, copyright;
    ArrayList<String> genre;

    @Override
    public String toString() {
        return "App{" +
                "name='" + name + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", artistname='" + artistname + '\'' +
                ", url='" + url + '\'' +
                ", genre=" + genre +
                '}';
    }
}
