package edu.uncc.cnnnews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/*
a. Assignment #. In class assignment 05
b. File Name. InClass05_Mylavarapu.zip
c. Full name of all students in your group. Sai Yesaswy Mylavarapu
*/
/**
 * Created by Hello on 2/13/2017.
 */

public class GetImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

    IImage activity;

    public GetImageAsyncTask(IImage activity) {
        this.activity = activity;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        InputStream in;
        try {
            URL url = new URL(params[0]);
            //URL is passed through params
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            in = con.getInputStream();

            Bitmap image = BitmapFactory.decodeStream(in);
            return image;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        activity.setupImage(result);
        super.onPostExecute(result);

    }

    static public interface IImage{
        public void setupImage(Bitmap result);
    }

}
