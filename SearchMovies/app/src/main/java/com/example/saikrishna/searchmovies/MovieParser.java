package com.example.saikrishna.searchmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by saikrishna on 10/16/17.
 */

public class MovieParser {
    static public class JSONParser {
        static ArrayList<Movies> parse(String in) throws JSONException {
            ArrayList<Movies> list = new ArrayList();
            JSONObject root = new JSONObject(in);
            JSONArray results = root.getJSONArray("results");
            for(int i = 0; i<results.length();i++){
                JSONObject obj = results.getJSONObject(i);
                Movies movies = new Movies();
                movies.Rating =obj.getString("vote_average");
                movies.Name =obj.getString("title");
                movies.Popularity =obj.getString("popularity");
                movies.imgurl = obj.getString("poster_path");
                movies.Overview= obj.getString("overview");
                movies.year=obj.getString("release_date");
movies.isGold=false;

                list.add(movies);

            }

            Log.d("SENDING LIST ARRAY:",list.toString());


            return list;
        }
    }
}
