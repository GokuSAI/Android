package com.example.app.homework05;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jerin on 10/14/2017.
 */

public class TrackUtil {
    static public class TrackJSONParser{
        static ArrayList<Track> parseTrack(String in) throws JSONException {
            ArrayList<Track> tracks = new ArrayList();
            JSONObject root = new JSONObject(in);
            JSONObject results = root.getJSONObject("results");
            JSONObject trackMatches = results.getJSONObject("trackmatches");
            JSONArray trackJSONarr = trackMatches.getJSONArray("track");
            for (int i=0;i<trackJSONarr.length();i++){
                JSONObject trackJSONobj = trackJSONarr.getJSONObject(i);
                Track track = Track.createTrack(trackJSONobj);
                tracks.add(track);
            }
            return tracks;
        }
        static ArrayList<Track> parseSimilarTrack(String in) throws JSONException {
            ArrayList<Track> tracks = new ArrayList();
            JSONObject root = new JSONObject(in);
            JSONObject results = root.getJSONObject("similartracks");
            JSONArray trackJSONarr = results.getJSONArray("track");
            for (int i=0;i<trackJSONarr.length();i++){
                JSONObject trackJSONobj = trackJSONarr.getJSONObject(i);
                Track track = Track.createSimilarTrack(trackJSONobj);
                tracks.add(track);
            }
            return tracks;
        }
    }
}
