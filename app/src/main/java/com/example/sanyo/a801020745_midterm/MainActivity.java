package com.example.sanyo.a801020745_midterm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements GetAppAsync.Idata{
    ProgressDialog progressDialog;
    ListView listView;
    ArrayList<App> appArrayList;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        editText = findViewById(R.id.editTextGenre);
        editText.setText("All");
        new GetAppAsync(MainActivity.this).execute("http://rss.itunes.apple.com/api/v1/us/ios-apps/top-grossing/all/50/explicit.json");
    }

    @Override
    public void handle(ArrayList<App> apps) {
        progressDialog.dismiss();
        Log.d("demo", apps.toString());
        appArrayList = apps;
        if(apps.size() == 0){
            Toast.makeText(MainActivity.this, "No News Found", Toast.LENGTH_SHORT).show();
            listView.setAdapter(null);
        }
        else{
            AppAdapter appAdapter = new AppAdapter(this, R.layout.app_item, apps);
            listView.setAdapter(appAdapter);
            List<String> duplicates = new ArrayList<>();
            for(int i = 0; i<apps.size();i++){
                for(int k = 0; k < apps.get(i).genre.size();k++){
                    duplicates.add(apps.get(i).genre.get(k));
                }
            }
            Log.d("demo",duplicates.toString());
            final Set<String> uniqueGenres = new HashSet<>();
            uniqueGenres.addAll(duplicates);
            uniqueGenres.add("All");
            findViewById(R.id.buttonFilter).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Choose Genre");
                   final String []array = uniqueGenres.toArray(new String[uniqueGenres.size()]);
                    Arrays.sort(array);

                    builder.setItems(array, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ArrayList<App> sortedList = new ArrayList<>();
                              editText.setText(array[i]);
                              String genre = array[i];
                            for (App a: appArrayList
                                 ) {
                                for (String g: a.genre) {
                                    if(g.equals(genre)){
                                         sortedList.add(a);
                                    }
                                }

                            }
                            Log.d("demo",sortedList.toString());
                            AppAdapter appAdapter = new AppAdapter(MainActivity.this, R.layout.app_item, sortedList);
                            listView.setAdapter(appAdapter);
                        }
                    });
                    builder.show();
                }
            });

            Log.d("demo",uniqueGenres.toString());
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this,AppDetails.class);
                    intent.putExtra("ARTICLE_KEY",appArrayList.get(position));
                    startActivity(intent);
                }
            });

        }
    }
}
