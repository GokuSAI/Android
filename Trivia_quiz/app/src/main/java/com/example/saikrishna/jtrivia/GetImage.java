package com.example.saikrishna.jtrivia;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
/**
 * Created by saikrishna on 10/3/17.
 */

public class GetImage extends AsyncTask<String,Void,Bitmap> {
    ImageUI imageUI;
    public GetImage(ImageUI imageUI){
        this.imageUI = imageUI;
    }
    @Override

    protected Bitmap doInBackground(String... params) {
        try{
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Bitmap bitmap = BitmapFactory.decodeStream(con.getInputStream());
            return bitmap;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageUI.setImageUI(bitmap);
    }

    public interface ImageUI{
        public void setImageUI(Bitmap image);

    }
}
