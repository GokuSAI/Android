package com.example.app.homework05;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetDataAsyncTask.SetData{
    Button search;
    ListView listView;
    EditText track;
    String api = "41e9abfd16a406382e79e1992c913dd1";
    String url = new String();
    static String TRACKLIST_KEY = "TRACK";
    FavoriteAdapter adapter;
    MySharedPreference preference;
    ArrayList<Track> favoriteTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preference = new MySharedPreference(this);
        favoriteTrack = new ArrayList<>();
        favoriteTrack = preference.getItemList();
        search = (Button) findViewById(R.id.search);
        track = (EditText) findViewById(R.id.track);
        listView = (ListView)findViewById(R.id.listView);
        if(favoriteTrack!=null) {
            adapter = new FavoriteAdapter(this, R.layout.custom_layout, favoriteTrack);
            listView.setAdapter(adapter);
            adapter.setNotifyOnChange(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, TrackActivity.class);
                    intent.putExtra(TRACKLIST_KEY, favoriteTrack.get(position));
                    startActivity(intent);
                }
            });
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkConnection()){
                    url = "http://ws.audioscrobbler.com/2.0/?method=track.search" +
                            "&track=" + track.getText().toString() +
                            "&api_key=" + api + "&limit=20&format=json";
                    new GetDataAsyncTask(MainActivity.this).execute(url);
                }
                else{
                    Toast.makeText(MainActivity.this,"No Net Connection",Toast.LENGTH_SHORT);
                }
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
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.quit:
                finishAffinity();
                return true;
        }
        return true;
    }

    private boolean isNetworkConnection(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected())
            return true;
        return false;
    }

    @Override
    public void setupData(ArrayList<Track> tracks) {
        if(tracks.size() == 0){
            Toast.makeText(MainActivity.this,"No results found",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra(TRACKLIST_KEY, tracks);
            startActivity(intent);
        }
    }
}
