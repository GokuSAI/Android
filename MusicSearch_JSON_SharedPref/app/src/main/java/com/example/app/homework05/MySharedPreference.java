package com.example.app.homework05;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 10/14/2017.
 */

public class MySharedPreference {
    static String USER_PREFS = "ITEM";
    android.content.SharedPreferences.Editor editor = null;
    android.content.SharedPreferences preferences = null;
    Context context;

    public MySharedPreference(Context context){
        this.context = context;
        preferences = context.getApplicationContext().getSharedPreferences(USER_PREFS,Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void addTrack(Track track){
        List<Track> trackList = getItemList();
        if(trackList ==null){
            trackList = new ArrayList<Track>();
        }
        trackList.add(track);
        saveItem(trackList);
    }

    public void removeTrack(Track track){
        List<Track> trackArrayList = getItemList();
        if(trackArrayList != null){
            trackArrayList.remove(track);
            Log.d("remove","track shared");
            saveItem(trackArrayList);
        }
    }

    public void saveItem(List<Track> trackList){
        Gson gson = new Gson();
        String jsonItem = gson.toJson(trackList);
        editor.putString(USER_PREFS,jsonItem);
        editor.commit();
    }

    public ArrayList<Track> getItemList(){
        List<Track> trackList;
        if(preferences.contains(USER_PREFS)){
            String jsonItems = preferences.getString(USER_PREFS,null);
            Gson gson = new Gson();
            Track[] favoriteTracks = gson.fromJson(jsonItems,Track[].class);
            trackList = Arrays.asList(favoriteTracks);
            trackList = new ArrayList<Track>(trackList);
            return (ArrayList<Track>) trackList;
        }
        else
            return null;

    }

}
