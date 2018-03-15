package com.example.sanyo.a801020745_midterm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Collections;

public class AppDetails extends AppCompatActivity {

    TextView textViewName, textViewDate, textViewCopyright, textViewArtist, textViewGenre;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_details);
        textViewName = findViewById(R.id.textViewTitle);
        textViewDate = findViewById(R.id.textViewDate);
        textViewArtist = findViewById(R.id.textViewArtist);
        textViewCopyright = findViewById(R.id.textViewCopyright);
        textViewGenre = findViewById(R.id.textViewGenre);
        imageView = findViewById(R.id.imageView2);

        App app = (App) getIntent().getExtras().getSerializable("ARTICLE_KEY");
        if(app != null){

            textViewName.setText(app.name);
            textViewDate.setText(app.releaseDate);
            textViewArtist.setText(app.artistname);
            String genre = "";
            for(int i =0; i<app.genre.size()-1; i++){
                genre += app.genre.get(i) + ", ";
            }
            genre = genre + app.genre.get(app.genre.size()-1);
            textViewGenre.setText(genre);
            textViewCopyright.setText(app.copyright);

            Picasso.get().load(app.url).into(imageView);
        }
        else{
            Toast.makeText(this,"Null app received",Toast.LENGTH_SHORT);
        }
    }
}
