package com.example.saikrishna.inclass09;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by saikrishna on 11/6/17.
 */

public class ThreadTask extends AsyncTask<String,Void,ArrayList<Threadclass>> {

    SharedPreferences pref;
    ThreadActivity threadActivity;
    LinkData linkData;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    ArrayList<Threadclass> threadclasses = new ArrayList<>();
//    pref = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

    public ThreadTask(ThreadActivity threadActivity,LinkData linkData) {
        this.threadActivity=threadActivity;
        this.linkData = linkData;
    }

    @Override
    protected void onPostExecute(ArrayList<Threadclass> threadclasses) {
        super.onPostExecute(threadclasses);
        linkData.printLoggedUserDetails(threadclasses);
    }

    @Override
    protected ArrayList<Threadclass> doInBackground(String... params) {
        threadclasses = new ArrayList<>();
        try {

            RequestBody formBody = new FormBody.Builder()
                    .build();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(params[0]).addHeader("Authorization","BEARER "+params[1])
                    .build();
            Response response = client.newCall(request).execute();
            User user = new User();
            //Log.d("demo",response.body().string());
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response.body().string());

            JSONObject js = new JSONObject(response.body().string());
            //Log.d("RESps",response.body().string().);

            if (js.getString("status").equals("ok")) {
                //Log.d("token", js.getString("token"));
                //user = gson.fromJson(response.body().string(), User.class);
                JSONArray arr = js.getJSONArray("threads");
                for(int i=0;i<arr.length();i++){
                    Threadclass threadclass = new Threadclass();
                    JSONObject obj = arr.getJSONObject(i);
                    threadclass.setTitle(obj.getString("title"));
                    threadclass.setUserid(obj.getString("user_id"));
                    threadclass.setID(obj.getString("id"));
                   // Log.d("Title",threadclass.toString());
                    //threadclass.setFirstname(obj);
                    threadclasses.add(threadclass);
                }
                //user.setToken(js.getString("token"));
                //user.setStatus("ok");
            } else {
                Log.d("message", js.getString("message"));
                user.setStatus("error");
                user.setMessage(js.getString("message"));
            }
            return threadclasses;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public  interface LinkData{
        void printLoggedUserDetails(ArrayList<Threadclass> th);
    }
}
