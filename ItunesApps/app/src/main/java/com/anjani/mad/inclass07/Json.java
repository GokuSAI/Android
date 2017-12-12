package com.anjani.mad.inclass07;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 9/25/2017.
 */

public class Json {
    static public class TrackJSONParser {
        static ArrayList<AppData> parseTracks(String in) throws JSONException {
            ArrayList<AppData> dataList = new ArrayList<AppData>();
            JSONObject root = new JSONObject(in).getJSONObject("feed");

            JSONArray entry = (JSONArray) root.getJSONArray("entry");
            // JSONArray resultsArray=root.getJSONArray("results");


            for (int i = 0; i < entry.length(); i++) {

                JSONObject trackJSONObject = entry.getJSONObject(i);
                AppData data = new AppData();
                JSONObject object = (JSONObject) trackJSONObject.getJSONObject("im:name");
                data.setAppName(object.getString("label"));

                JSONObject object1 = (JSONObject) trackJSONObject.getJSONObject("im:price");

                JSONObject object2 = (JSONObject) object1.getJSONObject("attributes");
                data.setAppPrice(object2.getString("amount"));

                JSONArray array = trackJSONObject.getJSONArray("im:image");

                JSONObject object3 = (JSONObject) array.getJSONObject(0);
                JSONObject object4 = (JSONObject) array.getJSONObject(2);

                data.setImage(object3.getString("label"));
                data.setLimage(object4.getString("label"));

                dataList.add(data);
            }
            return dataList;
        }
    }
}