package com.example.saikrishna.jtrivia;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Created by saikrishna on 10/3/17.
 */

public class QuestionUtil {
    static public class QuestionJSONParser{
        static ArrayList<Questions> parseQuestion(String in) throws JSONException {
            ArrayList<Questions> newslist = new ArrayList();
            JSONObject root = new JSONObject(in);
            JSONArray newsJSONarr = root.getJSONArray("questions");
            for(int i=0;i<newsJSONarr.length();i++){
                JSONObject newJSONobj = newsJSONarr.getJSONObject(i);
                Questions questions = Questions.createQuestion(newJSONobj);
                newslist.add(questions);
            }
            return newslist;
        }
    }
}
