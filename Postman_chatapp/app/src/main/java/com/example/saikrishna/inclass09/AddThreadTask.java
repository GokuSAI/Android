package com.example.saikrishna.inclass09;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

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

/**
 * Created by saikrishna on 11/7/17.
 */

public class AddThreadTask  extends AsyncTask<String,Void,ArrayList<Threadclass>> {

    SharedPreferences pref;
    ThreadActivity threadActivity;

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    ArrayList<Threadclass> threadclasses = new ArrayList<>();

    public AddThreadTask(ThreadActivity threadActivity) {
        this.threadActivity = threadActivity;

    }

    @Override
    protected void onPostExecute(ArrayList<Threadclass> threadclasses) {
        super.onPostExecute(threadclasses);
        threadActivity.printAddedUserDetails(threadclasses);
    }

    @Override
    protected ArrayList<Threadclass> doInBackground(String... params) {
        threadclasses = new ArrayList<>();
        try {

            RequestBody formBody = new FormBody.Builder()
                    .add("title",params[2])
                    .build();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(params[0]).addHeader("Authorization", "BEARER " + params[1]).post(formBody)
                    .build();
            Response response = client.newCall(request).execute();
            User user = new User();

            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response.body().string());

            JSONObject js = new JSONObject(response.body().string());


            if (js.getString("status").equals("ok")) {

                JSONObject job = js.getJSONObject("thread");

                Threadclass threadclass = new Threadclass();
                    threadclass.setTitle(job.getString("title"));
                    threadclass.setUserid(job.getString("user_id"));




                //This is the thread ID that we need in the chatroom activity
                    threadclass.setID(job.getString("id"));


                    threadclasses.add(threadclass);
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
        return null;
    }

    public interface AddLinkData {
        void printAddedUserDetails(ArrayList<Threadclass> th);
    }
}


