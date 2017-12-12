package edu.uncc.cnnnews;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/*
a. Assignment #. In class assignment 05
b. File Name. InClass05_Mylavarapu.zip
c. Full name of all students in your group. Sai Yesaswy Mylavarapu
*/

public class GetNewsAsyncTask extends AsyncTask<String, Void, ArrayList<CNNNews>> {

    Idata activity;

    public GetNewsAsyncTask(Idata activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<CNNNews> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            int statusCode = con.getResponseCode();

            if (statusCode == HttpURLConnection.HTTP_OK) {
                InputStream in = con.getInputStream();
                return NewsUtil.NewsPullParser.newsParser(in);
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(ProtocolException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<CNNNews> result) {
        Log.d("demo",result.toString());
        activity.setupData(result);
        super.onPostExecute(result);
    }

    static public interface Idata{
        public void setupData(ArrayList<CNNNews> result);
    }
}
