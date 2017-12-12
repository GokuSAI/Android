package com.example.saikrishna.inclass09;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by saikrishna on 11/6/17.
 */

public class SignupTask extends AsyncTask<String,Void,User> {
    Signupactivity signup;
    private final Gson gson = new Gson();

    public SignupTask(Signupactivity signup) {
        this.signup=signup;
    }


    @Override
    protected void onPostExecute(User user) {

        if(user!=null){
            Log.d("userdata", user.toString());
            signup.printSignedInUserDetails(user);
        }

        else {
        }

            super.onPostExecute(user);
    }

    @Override
    protected User doInBackground(String... params) {

        User user = new User();

        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("email",params[1]).add("password",params[2]).add("fname",params[3]).add("lname",params[4])
                    .build();
            OkHttpClient client =new OkHttpClient();
            Request request = new Request.Builder()
                    .url(params[0]).post(formBody)
                    .build();
            Response response = client.newCall(request).execute();

            //Log.d("demo",response.body().string());
            //if (!response.isSuccessful()) throw new IOException("Unexpected code " + response.body().string());

            JSONObject js = new JSONObject(response.body().string());
            //Log.d("RESps",response.body().string().);

            if (js.getString("status").equals("ok")) {
                Log.d("token", js.getString("token"));
                //user = gson.fromJson(response.body().string(), User.class);

                user.setToken(js.getString("token"));
                user.setStatus("ok");
                user.setUserFname(js.getString("user_fname"));
                user.setUserId(js.getString("user_id"));
                user.setUserLname(js.getString("user_lname"));
            }
            else {
                Log.d("message",js.getString("message"));
                user.setStatus("error");
                user.setMessage(js.getString("message"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;

    }
    public  interface LinkSignedInData{
        void printSignedInUserDetails(User user);
    }
}

