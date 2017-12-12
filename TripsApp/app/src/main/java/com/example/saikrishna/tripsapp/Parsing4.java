package com.example.saikrishna.tripsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by saikrishna on 12/4/17.
 */

public class Parsing4 {
    static public class JSONParser {
        static String parseplace(String in) throws JSONException {
            String placeid = null;
            ArrayList<NearbyCities> nbclist = new ArrayList();

            JSONObject root = new JSONObject(in);
            JSONArray JSONarr = root.getJSONArray("predictions");
            for(int i=0;i<JSONarr.length();i++){


                JSONObject placesJSONobj = JSONarr.getJSONObject(i);
                Log.d("city",AddPlaces.City);
                Log.d("desc",placesJSONobj.getString("description"));
                if(placesJSONobj.getString("description").equals(AddPlaces.City)) {

                    placeid = placesJSONobj.getString("place_id");
                }



            }
            return placeid;
        }
    }
}
