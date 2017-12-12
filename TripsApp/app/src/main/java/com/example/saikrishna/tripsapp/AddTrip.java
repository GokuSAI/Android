package com.example.saikrishna.tripsapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class AddTrip extends AppCompatActivity {

    static EditText tripname,placename;
    Button search,addtrip;
    static ArrayList<NearbyPlaces> nbp;
    static  ArrayList<NearbyCities> nbc;
    RecyclerView Triprecyclerview;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref;
    String baseurl="https://maps.googleapis.com/maps/api/place/details/json";
    String googlekey="AIzaSyBj4zVnoOYXok413AIWupcSEm_KapQxYh8";
    String placeidurl="https://maps.googleapis.com/maps/api/place/autocomplete/json";

    String Link = placeidurl+"?key="+googlekey+"&types=(cities)&input=";
    String coordinateslink = baseurl+"?key="+googlekey+"&placeid=";


    String nearbyplaceslink = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key="+googlekey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        childref = dataref.child("trips");

        tripname = (EditText)findViewById(R.id.Trip_edittext);
        placename=(EditText)findViewById(R.id.Place_edittext);
        search=(Button)findViewById(R.id.Search_button);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnection()) {
                    String finalurl = Link+placename.getText().toString();
                    Log.d("Link",finalurl);
                    new GetGEO().execute(finalurl);
                } else {
                    Toast.makeText(getApplicationContext(), "No network connection", Toast.LENGTH_SHORT);
                }
            }
        });
        addtrip=(Button)findViewById(R.id.AddTrip_button);
        Triprecyclerview=(RecyclerView)findViewById(R.id.Trip_recyclerview);



        addtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tripname.getText().toString()== null && placename.getText().toString()== null && tripname.getText().toString()==" "&& placename.getText().toString()==" " ){

                    Toast.makeText(AddTrip.this, "Please Enter all the fields", Toast.LENGTH_SHORT).show();


                }
                else{
                    Places places = new Places();
                    places.setName(tripname.getText().toString());
                    places.setPlace(placename.getText().toString());



                    // places.setAreas();
                    DatabaseReference newRef = childref.push();
                    String id = String.valueOf(newRef);
                    String idnote = id.substring(id.indexOf("trips/-")+6).trim();

                    places.setId(idnote);

                    newRef.setValue(places);
                }
            }
        });
    }
    private boolean isNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        return false;
    }

     class  GetGEO extends AsyncTask<String, Integer, ArrayList<NearbyCities>>{

        @Override
        protected ArrayList<NearbyCities> doInBackground(String... params) {
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
                return Parsing1.JSONParser.parseplace(sb.toString());

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
        protected void onPostExecute(ArrayList<NearbyCities> nearbyCities) {
            Log.d("nearbycities",nearbyCities.toString());

            nbc = nearbyCities;
            String s =nearbyCities.get(1).getPlaceid();

            Triprecyclerview.setLayoutManager(new LinearLayoutManager(AddTrip.this));

            Tripadapter adapter = new Tripadapter(AddTrip.this,nearbyCities);
            Triprecyclerview.setAdapter(adapter);
            String geolink =coordinateslink+s;
            if (isNetworkConnection()) {
                //Log.d("Geolink",geolink);
                new Getlatlong().execute(geolink);
            } else {
                Toast.makeText(getApplicationContext(), "No network connection", Toast.LENGTH_SHORT);
            }
            super.onPostExecute(nearbyCities);
        }



        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }


    class Getlatlong extends  AsyncTask<String,Integer,String>{

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
            Log.d("Latlng",s);
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
            AddTrip.nbp= nearbyPlaces;
            Log.d("NearbyPlaces",nearbyPlaces.toString());
            super.onPostExecute(nearbyPlaces);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
