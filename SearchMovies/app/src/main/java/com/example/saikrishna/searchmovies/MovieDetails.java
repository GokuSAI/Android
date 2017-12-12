package com.example.saikrishna.searchmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class MovieDetails extends AppCompatActivity {
    String key1 = "TEXT_KEY";
    Movies received;
    TextView title,overview,date,rating;
    ImageView img;
    public static String PREFNAME = "MusicPref";
    public static String PREFKEY = "MYKEYSECRET";
    private ArrayList<Movies> s1;


    SharedPreferences mypref;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences.Editor editor;
        ArrayList<Movies> items1ArrayList;
        Gson gson;
        String json;
        switch (item.getItemId()){
            case R.id.home:
                editor = mypref.edit();
                String fav = mypref.getString(PREFKEY, null);
                ArrayList<Movies> temp = new ArrayList<Movies>();
                if(fav!=null) {
                    gson = new Gson();
                    Movies[] arr = gson.fromJson(fav, Movies[].class);
                    if(arr!=null)
                        temp = new ArrayList<>(Arrays.asList(arr));

                }
                for(Movies ite: s1){
                    if(temp.contains(ite)){
                        temp.remove(ite);
                    }

                }
                items1ArrayList = temp;
                for(Movies it: s1){
                    if(it.isGold){
                        if(items1ArrayList.size()<20) {
                            //  Toast.makeText(this, "Added to favourites", Toast.LENGTH_SHORT).show();
                            items1ArrayList.add(it);
                        }
                        else{
                            Toast.makeText(this, "20 limit reached", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                gson = new Gson();
                json = gson.toJson(items1ArrayList);

                editor.putString(PREFKEY,json);
                editor.commit();


                Intent intent = new Intent(MovieDetails.this,MainActivity.class);
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
        setContentView(R.layout.activity_movie_details);
        mypref = this.getSharedPreferences(PREFNAME,MODE_PRIVATE);

        title = (TextView)findViewById(R.id.T_Title);
        overview =(TextView)findViewById(R.id.T_Overview);
        date=(TextView)findViewById(R.id.T_ReleaseDate);
        rating=(TextView)findViewById(R.id.T_Rating);
        img = (ImageView)findViewById(R.id.poster);

        if (getIntent().getExtras() != null) {
            received = (Movies) getIntent().getExtras().get(key1);
            Log.d("RECEIVED",received.toString());
        }

        title.setText(received.getName());
        overview.setText(received.getOverview());
        date.setText(received.getYear());
        rating.setText(received.getRating());
        if(received.getImgurl().equals("")){
            img.setImageResource(R.drawable.default_image);

        }
        else {
            Picasso.with(MovieDetails.this).load("http://image.tmdb.org/t/p/w342" + received.getImgurl()).into(img);
        }






    }
}
