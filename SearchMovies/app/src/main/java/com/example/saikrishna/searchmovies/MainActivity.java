package com.example.saikrishna.searchmovies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    static String key1 = "TEXT_KEY";
    String url1,url2,URL;
    EditText SearchedWord;
    Button SearchButton;
    private ListView ls;

    String text;
    public ArrayList<Movies> s1;
    CustomAdapter customAdapter;
    ProgressDialog pb;
    public static String PREFNAME = "MusicPref";
    public static String PREFKEY = "MYKEYSECRET";


    SharedPreferences mypref;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.rating :
                Toast.makeText(this, "home clicked", Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.popularity :
                Toast.makeText(this, "home clicked", Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.favorites :
                Intent intent = new Intent(MainActivity.this,FavoriteMovies.class);
                startActivity(intent);
                //Toast.makeText(this, "home clicked", Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.quit:
                finishAffinity();
                //Toast.makeText(this, "quit clicked", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mypref = this.getSharedPreferences(PREFNAME,MODE_PRIVATE);
        s1=new ArrayList<>();

        SearchedWord = (EditText)findViewById(R.id.SearchEditText);
        SearchButton = (Button)findViewById(R.id.SearchButton);
        ls =(ListView)findViewById(R.id.MainListView);
        //Building The URL
        url1="https://api.themoviedb.org/3/search/movie?query=";
        url2="&api_key=6509825815f32b93841731adf2be87e8&page=1";
        mypref = this.getSharedPreferences(FavoriteMovies.PREFNAME, MODE_PRIVATE);
        String fav = mypref.getString(PREFKEY, null);
        s1= new ArrayList<Movies>();

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = SearchedWord.getText().toString();
                if(text.equals("")) {
                    Toast.makeText(MainActivity.this, "No Input", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d("URL_LINK",url1+text+url2);
                    URL =url1+text+url2;
                    if(isNetworkConnection()){
                        new GetDataAsync().execute(URL);

                    }
                    else{
                        Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }


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

    class GetDataAsync extends AsyncTask<String,Integer,ArrayList<Movies>>{
        BufferedReader reader = null;

        @Override
        protected ArrayList<Movies> doInBackground(String... params) {
            StringBuilder builder = new StringBuilder();
            try {
                java.net.URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int statusCode = connection.getResponseCode();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                }
                return MovieParser.JSONParser.parse(builder.toString());
            }catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
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
            pb = ProgressDialog.show(MainActivity.this,"loading","please wait",true);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<Movies> movies) {
            super.onPostExecute(movies);
            pb.dismiss();
            if(null == movies|| movies.size()==0){
                Toast.makeText(MainActivity.this, "No results Found", Toast.LENGTH_SHORT).show();

            }
            else{
                s1 = movies;

                SharedPreferences.Editor editor = mypref.edit();
                String fav = mypref.getString(PREFKEY, null);
                ArrayList<Movies> temp = new ArrayList<Movies>();
                if (fav != null) {
                    Gson gson = new Gson();
                    Movies[] arr = gson.fromJson(fav, Movies[].class);
                    if (arr != null)
                        temp = new ArrayList<>(Arrays.asList(arr));

                }
                //temp-fav array list
                //s1-parsing array list
                for (Movies ite : s1) {
                    if (temp.contains(ite)) {
                        ite.setGold(true);
                    }

                }
                Log.d("entered","POStEXECUTE");
                customAdapter = new CustomAdapter(MainActivity.this,R.layout.listviewlayout, s1);
                ls.setAdapter(customAdapter);

                ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        SharedPreferences.Editor editor = mypref.edit();
                        mypref = MainActivity.this.getSharedPreferences(MainActivity.PREFNAME, MODE_PRIVATE);
                        String fav = mypref.getString(PREFKEY, null);
                        ArrayList<Movies> temp = new ArrayList<Movies>();
                        if (fav != null) {
                            Gson gson = new Gson();
                            Movies[] arr = gson.fromJson(fav, Movies[].class);
                            if (arr != null)
                                temp = new ArrayList<>(Arrays.asList(arr));

                        }
                        for(Movies ite :s1 ){
                            if(temp.contains(ite))
                                temp.remove(ite);
                        }
                        ArrayList<Movies> items1ArrayList = temp;
                        for (Movies item : s1) {
                            Log.d("CHECKING",item.toString());
                            if (item.getGold()) {

                                //Toast.makeText(SearchResults.this, "Added to favourites", Toast.LENGTH_SHORT).show();
                                items1ArrayList.add(item);

                            }

                        }
                        Gson gson = new Gson();
                        String json = gson.toJson(items1ArrayList);

                        editor.putString(PREFKEY, json);
                        editor.commit();

                        Intent intent = new Intent(MainActivity.this, MovieDetails.class);

                        intent.putExtra(key1, s1.get(position));
                        Log.d("SEARCH SENDING",s1.get(position).toString());
                        startActivity(intent);

                    }
                });
            }
        }
    }
}
