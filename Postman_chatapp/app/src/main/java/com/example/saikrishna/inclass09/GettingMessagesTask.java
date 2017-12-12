package com.example.saikrishna.inclass09;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

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

public class GettingMessagesTask  extends AsyncTask<String,Void,ArrayList<MessageClass>> {

        SharedPreferences pref;
        ChatroomActivity chatActivity;
public static final String MY_PREFS_NAME = "MyPrefsFile";

        ArrayList<MessageClass> MessageClasses = new ArrayList<>();
//    pref = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

public GettingMessagesTask(ChatroomActivity chatActivity) {
        this.chatActivity=chatActivity;
        }

@Override
protected void onPostExecute(ArrayList<MessageClass> MessageClasses) {
        super.onPostExecute(MessageClasses);
        chatActivity.printmessages(MessageClasses);
        }

@Override
protected ArrayList<MessageClass> doInBackground(String... params) {
        MessageClasses = new ArrayList<>();
        try {

        RequestBody formBody = new FormBody.Builder()
        .build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
        .url(params[0]).addHeader("Authorization","BEARER "+params[1])
        .build();
        Response response = client.newCall(request).execute();
        User user = new User();
        //Log.d("messagetask",response.body().string());
        if (!response.isSuccessful())
        throw new IOException("Unexpected code " + response.body().string());

        JSONObject js = new JSONObject(response.body().string());
        //Log.d("RESps",response.body().string().);

        if (js.getString("status").equals("ok")) {


                JSONArray arr = js.getJSONArray("messages");
                for(int i=0;i<arr.length();i++) {
                        JSONObject job = arr.getJSONObject(i);

                        MessageClass msg = new MessageClass();
                        msg.setFirstname(job.getString("user_fname"));
                        msg.setLastname(job.getString("user_lname"));
                        msg.setUserid(job.getString("user_id"));
                        msg.setMsgID(job.getString("id"));
                        msg.setMessagecontent(job.getString("message"));
                        msg.setCreatedtime(job.getString("created_at"));
                        MessageClasses.add(msg);

                }

        }        return MessageClasses;

                //user.setToken(js.getString("token"));
        //user.setStatus("ok");
        } catch (JSONException e1) {
                e1.printStackTrace();
        } catch (IOException e1) {
                e1.printStackTrace();
        }

        return  null;
        }


public  interface LinkData{
    void printmessages(ArrayList<MessageClass> th);
}
}
