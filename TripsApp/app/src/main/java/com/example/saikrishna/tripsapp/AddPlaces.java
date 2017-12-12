package com.example.saikrishna.tripsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AddPlaces extends AppCompatActivity {

    ArrayList<String> placeidlist;
    String baseurl="https://maps.googleapis.com/maps/api/place/details/json";
    String googlekey="AIzaSyBj4zVnoOYXok413AIWupcSEm_KapQxYh8";
    String placeidurl="https://maps.googleapis.com/maps/api/place/autocomplete/json";

    String Link = placeidurl+"?key="+googlekey+"&types=(cities)&input=";
    String coordinateslink = baseurl+"?key="+googlekey+"&placeid=";


    String nearbyplaceslink = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key="+googlekey;
   static String City,ID;
    String latlng;

    RecyclerView myrecyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Log.d("list",AddTrip.nbp.toString());


        setContentView(R.layout.activity_add_places);
        if(getIntent().getExtras()!=null){
           City = (String) getIntent().getExtras().get("Place");
            ID = (String) getIntent().getExtras().get("ID");
        }
        String finalurl = Link+City;
        new Getplaceid().execute(finalurl);











    }
    public  void AdapterCall(List<NearbyPlaces> functionlist){
        myrecyclerview = (RecyclerView) findViewById(R.id.My_recycler_view);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        PlacesAdapter adapter1 = new PlacesAdapter(this,functionlist,ID);
        myrecyclerview.setAdapter(adapter1);
    }



    class  Getplaceid extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            try {


                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int statusCode = con.getResponseCode();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                }

                publishProgress(100);
                return Parsing4.JSONParser.parseplace(sb.toString());

                //return RecipeUtil.RecipeJSONParser.parseRecipe(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null)
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
        }


        @Override
        protected void onPostExecute(String s) {


            if(s != null) {
                String geolink = coordinateslink + s;

                new Getlat().execute(geolink);
            }
            else{
                Toast.makeText(AddPlaces.this, "S value is"+s, Toast.LENGTH_SHORT).show();
                //Toast.makeText(AddPlaces.this, "Null latlng", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }


    class Getlat extends  AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... params) {
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            try {


                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int statusCode = con.getResponseCode();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                }

                publishProgress(100);
                return Parsing2.JSONParser.parseplace(sb.toString());

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null)
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
        }

        @Override
        protected void onPostExecute(String s) {
            //Log.d("Latlng",s);
            if (isNetworkConnection()) {
                String nearlink= nearbyplaceslink+"&location="+s+"&radius=1000";
                Log.d("nearlink",nearlink);
                new Getnearbyplaces().execute(nearlink);
            } else {
                Toast.makeText(getApplicationContext(), "No network connection", Toast.LENGTH_SHORT);
            }

            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    class Getnearbyplaces extends  AsyncTask< String , Integer, ArrayList<NearbyPlaces>>{

        @Override
        protected ArrayList<NearbyPlaces> doInBackground(String... params) {
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            try {


                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int statusCode = con.getResponseCode();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                }

                publishProgress(100);
                return Parsing3.JSONParser.parseplace(sb.toString());

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null)
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
        }

        @Override
        protected void onPostExecute(ArrayList<NearbyPlaces> nearbyPlaces) {
            AdapterCall(nearbyPlaces);

            Log.d("NearbyPlaces",nearbyPlaces.toString());
            super.onPostExecute(nearbyPlaces);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
    private boolean isNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        return false;
    }

}
