package com.example.saikrishna.tripsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by saikrishna on 12/4/17.
 */

public class Parsing3 {
    static public class JSONParser {
        static  ArrayList<NearbyPlaces> parseplace(String in) throws JSONException {

            ArrayList<NearbyPlaces> list = new ArrayList();
            JSONObject root = new JSONObject(in);
            JSONArray JSONarr = root.getJSONArray("results");
            for(int i=0;i<JSONarr.length();i++){
                NearbyPlaces nbp = new NearbyPlaces();
                JSONObject newJSONobj = JSONarr.getJSONObject(i);
                String icon = newJSONobj.getString("icon");
                String s = newJSONobj.getString("name");
                nbp.setName(s);
                nbp.setIcon(icon);
                list.add(nbp);
            }
            return  list;

        }
    }
}
