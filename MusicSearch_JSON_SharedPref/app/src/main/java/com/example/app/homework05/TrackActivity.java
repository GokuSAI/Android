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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrackActivity extends AppCompatActivity implements GetSimilarDataAsyncTask.SetData{
    ListView listView;
    CustomAdapter adapter;
    String url = new String();
    String api = "41e9abfd16a406382e79e1992c913dd1";
    TextView name,artist,trackURL;
    ImageView imageView;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        name = (TextView) findViewById(R.id.name);
        artist = (TextView) findViewById(R.id.artist);
        trackURL = (TextView) findViewById(R.id.url) ;
        imageView = (ImageView) findViewById(R.id.imageView);
        final Track tracks = getIntent().getParcelableExtra(SearchActivity.TRACK_KEY);
        name.setText(tracks.getName());
        artist.setText(tracks.getArtist());
        trackURL.setText(tracks.getUrl());
        if(!tracks.getLargeImageURL().equals(""))
            Picasso.with(TrackActivity.this).load(tracks.getLargeImageURL()).into(imageView);
        else
            imageView.setImageResource(R.drawable.default_image);
        url = "http://ws.audioscrobbler.com/2.0/?method=track.getsimilar" +
                "&track=" + name.getText().toString() +
                "&artist=" + artist.getText().toString() +
                "&api_key=" + api + "&limit=10&format=json";
        new GetSimilarDataAsyncTask(TrackActivity.this).execute(url);

    }

    @Override
    public void setupData(final ArrayList<Track> tracks) {
        preferences = this.getApplicationContext().getSharedPreferences(MySharedPreference.USER_PREFS, Context.MODE_PRIVATE);
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
        listView = (ListView)findViewById(R.id.similarTrackListView);
        adapter = new CustomAdapter(TrackActivity.this,R.layout.custom_layout,tracks);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TrackActivity.this,TrackActivity.class);
                intent.putExtra(SearchActivity.TRACK_KEY,tracks.get(position));
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
                Intent intent = new Intent(TrackActivity.this,MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.quit:
                finishAffinity();
                return true;
        }
        return true;
    }
}
