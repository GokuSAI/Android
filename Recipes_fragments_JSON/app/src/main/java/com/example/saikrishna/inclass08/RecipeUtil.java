package com.example.saikrishna.inclass08;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by saikrishna on 10/30/17.
 */

public class RecipeUtil {
    static public class RecipeJSONParser{
        static ArrayList<Recipes> parseRecipe(String in) throws JSONException {
            ArrayList<Recipes> newslist = new ArrayList();
            JSONObject root = new JSONObject(in);
            JSONArray newsJSONarr = root.getJSONArray("results");
            for(int i=0;i<newsJSONarr.length();i++){
                JSONObject newJSONobj = newsJSONarr.getJSONObject(i);
                Recipes recipes = Recipes.createRecipe(newJSONobj);
                newslist.add(recipes);
            }
            return newslist;
        }
    }
}