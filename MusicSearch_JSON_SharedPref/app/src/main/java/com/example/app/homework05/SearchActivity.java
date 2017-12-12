package com.example.app.homework05;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ListView listView;
    CustomAdapter adapter;
    static String TRACK_KEY = "TRACK";
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        preferences = this.getApplicationContext().getSharedPreferences(MySharedPreference.USER_PREFS, Context.MODE_PRIVATE);
        final ArrayList<Track> tracks = getIntent().getParcelableArrayListExtra(MainActivity.TRACKLIST_KEY);
        List<Track> trackList = new ArrayList<Track>();
        if(preferences.contains(MySharedPreference.USER_PREFS)){
            String jsonItems = preferences.getString(MySharedPreference.USER_PREFS,null);
            Gson gson = new Gson();
            Track[] favoriteTracks = gson.fromJson(jsonItems,Track[].class);
            //trackList = Arrays.asList(favoriteTracks);
            trackList = new ArrayList<>(Arrays.asList(favoriteTracks));
        }
        for (Track ite : tracks) {
            if (trackList.contains(ite)) {
                ite.setFavorite(true);
            }
        }
        listView = (ListView)findViewById(R.id.searchResultListView);
        adapter = new CustomAdapter(SearchActivity.this,R.layout.custom_layout,tracks);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this,TrackActivity.class);
                intent.putExtra(TRACK_KEY,tracks.get(position));
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.home:
                Intent intent = new Intent(SearchActivity.this,MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.quit:
                finishAffinity();
                return true;
        }
        return true;
    }
}
