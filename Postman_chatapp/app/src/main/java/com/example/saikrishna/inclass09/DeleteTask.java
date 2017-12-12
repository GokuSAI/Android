package com.example.saikrishna.inclass09;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

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

public class DeleteTask extends AsyncTask<String,Void,Void> {

    SharedPreferences pref;
    ThreadActivity threadActivity;

    public static final String MY_PREFS_NAME = "MyPrefsFile";


    public DeleteTask(ThreadActivity threadActivity) {
        this.threadActivity = threadActivity;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(String... params) {
        try {

            RequestBody formBody = new FormBody.Builder()
                    .build();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(params[0]).addHeader("Authorization", "BEARER " + params[1])
                    .build();
            Response response = client.newCall(request).execute();
            User user = new User();
            //Log.d("demo",response.body().string());
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response.body().string());

            //JSONObject js = new JSONObject(response.body().string());
            //Log.d("RESps",response.body().string().);


            //return threadclasses;
        }  catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}

