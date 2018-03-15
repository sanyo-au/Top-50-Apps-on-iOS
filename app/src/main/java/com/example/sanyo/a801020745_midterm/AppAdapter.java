package com.example.sanyo.a801020745_midterm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sanyo on 3/12/2018.
 */

public class AppAdapter extends ArrayAdapter<App> {
    public AppAdapter(@NonNull Context context, int resource, @NonNull List<App> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        App app = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){ //if no view to re-use then inflate a new one
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewName = convertView.findViewById(R.id.textViewName);
            viewHolder.textViewDate = convertView.findViewById(R.id.textViewDate);
            viewHolder.imageUrl = convertView.findViewById(R.id.imageView);
            viewHolder.textViewArtist = convertView.findViewById(R.id.textViewArtist);
            viewHolder.textViewGenre = convertView.findViewById(R.id.textViewGenres);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //set the data from the email object
        viewHolder.textViewName.setText(app.name);
        viewHolder.textViewDate.setText(app.releaseDate);
        viewHolder.textViewArtist.setText(app.artistname);
        String genre = "";
        for(int i =0; i<app.genre.size()-1; i++){
            genre += app.genre.get(i) + ", ";
        }
        genre = genre + app.genre.get(app.genre.size()-1);
        viewHolder.textViewGenre.setText(genre);
        Picasso.get().load(app.url).into(viewHolder.imageUrl);
        return convertView;

    }
    private static class ViewHolder{
        ImageView imageUrl;
        TextView textViewName;
        TextView textViewArtist;
        TextView textViewDate;
        TextView textViewGenre;
    }
}
