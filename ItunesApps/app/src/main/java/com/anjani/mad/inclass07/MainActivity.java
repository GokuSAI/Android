package com.anjani.mad.inclass07;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements AppAdapter.IData,Adapter.IData {

    ArrayList<AppData> mData;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView1;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DatabaseDataManager dm;
    Adapter newAdaptor;
    TextView t;
    ArrayList<AppData> mFavs;
    ProgressDialog pd;

    private void LoadData()
    {
        t = (TextView)findViewById(R.id.textView);
        t.setText("Ascending");
        final Switch myswitch = (Switch) findViewById(R.id.switch1);
        myswitch.setChecked(true);
        mData = new ArrayList<>();
        if (isConnectedOnline()) {
            try {
                mData = new Movie_parser_AsyncTask(MainActivity.this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json") .get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(MainActivity.this, "Not Connected to Internet", Toast.LENGTH_LONG).show();
        }

        dm = new DatabaseDataManager(this);
        mFavs = dm.getAllNotes();

        int delIndex = 0;

        for(int i = mData.size() - 1; i >= 0; i--)
        {
            if(mFavs.contains(mData.get(i)))
            {
                mData.remove(mData.get(i));
            }
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Collections.sort(mData, new Comparator<AppData>() {
            @Override
            public int compare(AppData movie, AppData t1) {
                return Double.compare(Double.parseDouble(movie.getAppPrice()),Double.parseDouble(t1.getAppPrice()));
            }
        });

        mAdapter = new AppAdapter(mData,this);
        pd.dismiss();
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView1 = (RecyclerView) findViewById(R.id.my_recycler_view1);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView1.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView1.setLayoutManager(mLayoutManager);


        if(mFavs.size() != 0) {
            ((TextView)findViewById(R.id.textViewNoFilter)).setVisibility(View.GONE);
            newAdaptor = new Adapter(mFavs,this);
            mRecyclerView1.setAdapter(newAdaptor);
        }
        else
        {
            mRecyclerView1.setVisibility(View.INVISIBLE);
            ((TextView)findViewById(R.id.textViewNoFilter)).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pd = ProgressDialog.show(MainActivity.this,"","Loading ...",true);
        LoadData();

        final Switch myswitch = (Switch) findViewById(R.id.switch1);

        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==false){

                    t.setText("Descending");
                    Collections.sort(mData, new Comparator<AppData>() {
                        @Override
                        public int compare(AppData movie, AppData t1) {
                            return Double.compare(Double.parseDouble(t1.getAppPrice()), Double.parseDouble(movie.getAppPrice()));
                        }
                    });
                    mAdapter.notifyDataSetChanged();

                }
                else{

                    //ascending
                    t.setText("Ascending");
                    Collections.sort(mData, new Comparator<AppData>() {
                        @Override
                        public int compare(AppData movie, AppData t1) {
                            return Double.compare(Double.parseDouble(movie.getAppPrice()),Double.parseDouble(t1.getAppPrice()));
                        }
                    });
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    public void onRefresh(View view)
    {
        LoadData();
    }

    private boolean isConnectedOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnected()) {
            return true;
        }
        return false;
    }



    @Override
    public void setUpData(boolean isFav,AppData data) {

        if(isFav == false)
        {
            ((TextView)findViewById(R.id.textViewNoFilter)).setVisibility(View.GONE);
            mRecyclerView1.setVisibility(View.VISIBLE);
            newAdaptor = new Adapter(dm.getAllNotes(),this);
            mRecyclerView1.setAdapter(newAdaptor);

        }else{

            mFavs = dm.getAllNotes();
            if(mFavs.size() == 0)
            {
                mRecyclerView1.setVisibility(View.INVISIBLE);
                ((TextView)findViewById(R.id.textViewNoFilter)).setVisibility(View.VISIBLE);
            }
            mData.add(data);

            if(((TextView)findViewById(R.id.textView)).getText() == "Ascending")
            {
                Collections.sort(mData, new Comparator<AppData>() {
                    @Override
                    public int compare(AppData movie, AppData t1) {
                        return Double.compare(Double.parseDouble(movie.getAppPrice()),Double.parseDouble(t1.getAppPrice()));
                    }
                });
            }
            else
            {
                Collections.sort(mData, new Comparator<AppData>() {
                    @Override
                    public int compare(AppData movie, AppData t1) {
                        return Double.compare(Double.parseDouble(t1.getAppPrice()), Double.parseDouble(movie.getAppPrice()));
                    }
                });
            }

            mAdapter = new AppAdapter(mData,this);
            mRecyclerView.setAdapter(mAdapter);

        }
    }
}
