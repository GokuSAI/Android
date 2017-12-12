package com.example.app.homework05;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by user on 10/15/2017.
 */

public class GetSimilarDataAsyncTask extends AsyncTask<String,Void,ArrayList<Track>> {
    BufferedReader reader = null;
    SetData setData;
    public GetSimilarDataAsyncTask(SetData setData){
        this.setData = setData;
    }
    @Override
    protected ArrayList<Track> doInBackground(String... params) {
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int statusCode = connection.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK){
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while((line=reader.readLine())!=null){
                    builder.append(line);
                }
            }
            return TrackUtil.TrackJSONParser.parseSimilarTrack(builder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(ArrayList<Track> tracks) {
        super.onPostExecute(tracks);
        if(tracks!=null){
            setData.setupData(tracks);
        }
    }

    static public interface SetData{
        public void setupData(ArrayList<Track> tracks);
    }
}
