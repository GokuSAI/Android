package com.example.saikrishna.inclass09;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by saikrishna on 11/7/17.
 */

public class AddMessageTask extends AsyncTask<String,Void,ArrayList<MessageClass>> {

    SharedPreferences pref;
    ChatroomActivity chatroomActivity;

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    ArrayList<MessageClass> MessageClasses = new ArrayList<>();
//    pref = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

    public AddMessageTask(ChatroomActivity chatroomActivity ) {
        this.chatroomActivity = chatroomActivity;

    }

    @Override
    protected void onPostExecute(ArrayList<MessageClass> MessageClasses) {
        super.onPostExecute(MessageClasses);
        chatroomActivity.printAddedUserDetails(MessageClasses);
    }

    @Override
    protected ArrayList<MessageClass> doInBackground(String... params) {
        MessageClasses = new ArrayList<>();
        try {

            RequestBody formBody = new FormBody.Builder()
                    .add("message",params[2])






                    .add("thread_id",params[3])//here we have to pass the thread Id, instead of 405







                    .build();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(params[0]).addHeader("Authorization", "BEARER " + params[1]).post(formBody)
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
                JSONObject job = js.getJSONObject("message");

                MessageClass msg = new MessageClass();
                msg.setMessagecontent(job.getString("message"));
                msg.setFirstname(job.getString("user_fname"));
                msg.setLastname(job.getString("user_lname"));
                msg.setUserid(job.getString("user_id"));
                msg.setMsgID(job.getString("id"));
                msg.setCreatedtime(job.getString("created_at"));

                // Log.d("Title",threadclass.toString());
                //threadclass.setFirstname(obj);
                MessageClasses.add(msg);
                //user.setToken(js.getString("token"));
                //user.setStatus("ok");
            } else {
                Log.d("message", js.getString("message"));
                user.setStatus("error");
                user.setMessage(js.getString("message"));
            }
            return MessageClasses;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface AddLinkData {
        void printAddedUserDetails(ArrayList<MessageClass> th);
    }
}

