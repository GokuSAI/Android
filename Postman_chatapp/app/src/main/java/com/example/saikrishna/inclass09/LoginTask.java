package com.example.saikrishna.inclass09;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

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

public class LoginTask extends AsyncTask<String,Void,User> {
    MainActivity mainActivity;
    private final Gson gson = new Gson();
    User loggedUser;

    public LoginTask(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
    }
    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);
        //Log.d("userdata",user.toString());
        mainActivity.printLoggedUserDetails(user);
    }
    @Override
    protected User doInBackground(String... params) {

        try {
        RequestBody formBody = new FormBody.Builder()
                .add("email",params[1]).add("password",params[2])
                .build();
        OkHttpClient client =new OkHttpClient();
        Request request = new Request.Builder()
                .url(params[0]).post(formBody)
                .build();
        Response response = client.newCall(request).execute();
            User user = new User();
            //Log.d("demo",response.body().string());
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response.body().string());

            JSONObject js = new JSONObject(response.body().string());
            //Log.d("RESps",response.body().string().);

            if (js.getString("status").equals("ok")) {
                Log.d("token", js.getString("token"));
                //user = gson.fromJson(response.body().string(), User.class);

                user.setUserFname(js.getString("user_fname"));
                user.setUserId(js.getString("user_id"));
                user.setUserLname(js.getString("user_lname"));
                user.setToken(js.getString("token"));
                user.setStatus("ok");
            }
            else {
                Log.d("message",js.getString("message"));
                user.setStatus("error");
                user.setMessage(js.getString("message"));
            }
            return user;
//        User user = gson.fromJson(response.body().string(), User.class);
//        return user;
    } catch (IOException e) {
        e.printStackTrace();
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }


        return null;
    }
    public  interface LinkData{
        void printLoggedUserDetails(User user);
    }
}
