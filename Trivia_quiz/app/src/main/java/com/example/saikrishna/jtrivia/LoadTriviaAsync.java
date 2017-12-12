package com.example.saikrishna.jtrivia;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
/**
 * Created by saikrishna on 10/3/17.
 */

public class LoadTriviaAsync extends AsyncTask<String,Void,ArrayList<Questions>> {
    int i=0 ;
    SettingUI setUI;
    public static ArrayList<Questions> questionList;

    public LoadTriviaAsync(SettingUI setUI){
        this.setUI = setUI;
    }
    @Override
    protected ArrayList<Questions> doInBackground(String... params) {
        BufferedReader reader = null;
        try{
            StringBuilder sb = new StringBuilder();
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int statusCode = con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK){
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line = "";
                while((line=reader.readLine())!=null){
                    sb.append(line);
                }
            }
            questionList = QuestionUtil.QuestionJSONParser.parseQuestion(sb.toString());
            return questionList;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        questionList = new ArrayList();
    }

    @Override
    protected void onPostExecute(ArrayList<Questions> question) {
        super.onPostExecute(question);
        if(question!=null){
            setUI.setUI(question);
        }
    }

    public interface SettingUI{
        public void setUI(ArrayList<Questions> question);

    }

}
