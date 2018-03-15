package com.example.sanyo.a801020745_midterm;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by sanyo on 3/12/2018.
 */

public class GetAppAsync extends AsyncTask<String, Void, ArrayList<App>>{
        Idata idata;

        public GetAppAsync(Idata idata) {
            this.idata = idata;
        }

        @Override
        protected void onPostExecute(ArrayList<App> apps) {
            super.onPostExecute(apps);
            idata.handle(apps);

        }

        @Override
        protected ArrayList<App> doInBackground(String... params) {
            HttpURLConnection connection = null;
            ArrayList<App> apps = new ArrayList<>();
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    String json = IOUtils.toString(connection.getInputStream(),"UTF8");
                    JSONObject root = new JSONObject(json);
                    JSONObject feed = root.getJSONObject("feed");
                    JSONArray appsJson = feed.getJSONArray("results");
                    for (int i = 0; i <appsJson.length() ; i++) {
                        JSONObject resultJson = appsJson.getJSONObject(i);
                        App app = new App();
                        app.artistname = resultJson.getString("artistName");
                        app.releaseDate = resultJson.getString("releaseDate");
                        app.name = resultJson.getString("name");
                        app.copyright = resultJson.getString("copyright");
                        app.url = resultJson.getString("artworkUrl100");
                        JSONArray genres = resultJson.getJSONArray("genres");
                        ArrayList<String> listOFGenres = new ArrayList<>();
                        for(int j = 0; j<genres.length(); j++) {
                            JSONObject genre = genres.getJSONObject(j);
                             listOFGenres.add(genre.getString("name"));
                        }
                        Collections.sort(listOFGenres);
                        app.genre = listOFGenres;
                        apps.add(app);
                    }
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
                if(connection !=null){
                    connection.disconnect();
                }
            }
            return apps;
        }
        public static interface Idata{
            public void handle(ArrayList<App> apps);
        }
    }


