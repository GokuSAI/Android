package com.example.saikrishna.searchmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.saikrishna.searchmovies.MainActivity.PREFKEY;

public class FavoriteMovies extends AppCompatActivity {
    CustomAdapter customAdapter;
    private ListView favlistview;
    ArrayList<Movies> s1;
    static String key1 = "TEXT_KEY";

    public static String PREFNAME = "MusicPref";
    public static String PREFKEY = "MYKEYSECRET";


    SharedPreferences mypref;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home :
                Intent intent = new Intent(FavoriteMovies.this,MainActivity.class);
                startActivity(intent);
                return  true;
            case R.id.popularity :
                Toast.makeText(this, "home clicked", Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.rating :

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
        setContentView(R.layout.activity_favorite_movies);

        favlistview = (ListView)findViewById(R.id.Favlistview);
        mypref = this.getSharedPreferences(FavoriteMovies.PREFNAME, MODE_PRIVATE);
        String fav = mypref.getString(PREFKEY, null);
        s1= new ArrayList<Movies>();
        if (fav != null) {
            Gson gson = new Gson();
            Movies[] arr = gson.fromJson(fav, Movies[].class);
            if (arr != null)
                s1 = new ArrayList<>(Arrays.asList(arr));


        }
        customAdapter = new CustomAdapter(this, R.layout.listviewlayout,s1);
        favlistview.setAdapter(customAdapter);
        favlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FavoriteMovies.this, MovieDetails.class);

                intent.putExtra(key1, s1.get(position));
                Log.d("SEARCH SENDING",s1.get(position).toString());
                startActivity(intent);
            }
        });
    }
}
