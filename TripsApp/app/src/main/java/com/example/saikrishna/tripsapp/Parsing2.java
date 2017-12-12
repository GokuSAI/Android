package com.example.saikrishna.tripsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by saikrishna on 12/4/17.
 */

public class Parsing2 {
    static public class JSONParser {
        static  String parseplace(String in) throws JSONException {
            String placeid = null;

            JSONObject root = new JSONObject(in);
            JSONObject child = root.getJSONObject("result");
            JSONObject geometry = child.getJSONObject("geometry");
            JSONObject location = geometry.getJSONObject("location");
            String lat = location.getString("lat");
            String lng = location.getString("lng");
            placeid=lat+","+lng;
            return placeid;
        }
    }
}
