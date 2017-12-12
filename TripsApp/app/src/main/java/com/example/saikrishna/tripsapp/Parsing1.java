package com.example.saikrishna.tripsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by saikrishna on 12/4/17.
 */

public class Parsing1 {
    static public class JSONParser {
        static ArrayList<NearbyCities> parseplace(String in) throws JSONException {
            String placeid = null;
            ArrayList<NearbyCities> nbclist = new ArrayList();

            JSONObject root = new JSONObject(in);
            JSONArray JSONarr = root.getJSONArray("predictions");
            for(int i=0;i<JSONarr.length();i++){
                NearbyCities nbc = new NearbyCities();

                JSONObject placesJSONobj = JSONarr.getJSONObject(i);

                nbc.setPlaceid(placesJSONobj.getString("place_id"));
                nbc.setDescription(placesJSONobj.getString("description"));
                nbclist.add(nbc);


            }
            return nbclist;
        }
    }
}
