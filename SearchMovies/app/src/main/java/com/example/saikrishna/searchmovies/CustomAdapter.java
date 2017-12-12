package com.example.saikrishna.searchmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.saikrishna.searchmovies.MainActivity.PREFKEY;

/**
 * Created by saikrishna on 10/16/17.
 */


public class CustomAdapter extends ArrayAdapter<Movies> implements  ImageView.OnClickListener {
    Context context;
    int mresource;
    ArrayList<Movies> arrayList;
    String fav;
    SharedPreferences mypref ;
    SharedPreferences.Editor editor;

    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Movies> objects) {
        super(context, resource, objects);

        this.context = context;
        this.mresource = resource;
        this.arrayList = (ArrayList<Movies>) objects;
        mypref = context.getSharedPreferences(MainActivity.PREFNAME, MODE_PRIVATE);
        editor = mypref.edit();
        fav = mypref.getString(PREFKEY, null);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.listviewlayout, null);

        if (arrayList != null){
            ImageView img1 = convertView.findViewById(R.id.imageView);
            TextView name = convertView.findViewById(R.id.textView);
            TextView name1 = convertView.findViewById(R.id.textView2);
            ImageView star = convertView.findViewById(R.id.starimage);
            if(arrayList.get(position).isGold){
                star.setImageResource(R.drawable.gold);
                star.setTag("gold");
            }
            else{
                star.setImageResource(R.drawable.silver);
                star.setTag("silver");
            }
            //http://image.tmdb.org/t/p/w154/kBf3g9crrADGMc2AMAMlLBgSm2h.jpg
            if(arrayList.get(position).getImgurl().equals("")){
                img1.setImageResource(R.drawable.default_image);
            }
            else {
                Picasso.with(context).load("http://image.tmdb.org/t/p/w154" + arrayList.get(position).getImgurl()).into(img1);
            }
            name.setText(arrayList.get(position).getName());
            name1.setText(arrayList.get(position).getYear());
            star.setOnClickListener(this);

        }

            return convertView;
    }

    @Override
    public void onClick(View v) {

        if(v.getTag().equals("silver")){
            ((ImageView)v).setTag("gold");
            ((ImageView) v).setImageResource(R.drawable.gold);
            Toast.makeText(context, "Added to fav", Toast.LENGTH_SHORT).show();
            ImageView v1 = (ImageView)v;
            ListView listView = (ListView)(((View)v1.getParent()).getParent());
            int pos= listView.getPositionForView(v);
            (arrayList.get(pos)).setGold(true);
            //mypref = context.getSharedPreferences(Searchdetails.PREFNAME, MODE_PRIVATE);

            ArrayList<Movies> temp = new ArrayList<Movies>();
            if (fav != null) {
                Gson gson = new Gson();
                Movies[] arr = gson.fromJson(fav, Movies[].class);
                if (arr != null)
                    temp = new ArrayList<>(Arrays.asList(arr));

            }
            //for(Tracks ite :arrayList){
            //    if(temp.contains(ite))
            //        temp.remove(ite);
            // }


            ArrayList<Movies> items1ArrayList = temp;


            //Toast.makeText(SearchResults.this, "Added to favourites", Toast.LENGTH_SHORT).show();
            items1ArrayList.add(arrayList.get(pos));

            Gson gson = new Gson();
            String json = gson.toJson(items1ArrayList);

            editor.putString(PREFKEY, json);
            editor.commit();

        }
        else{
            v.setTag("silver");
            ((ImageView)v).setImageResource(R.drawable.silver);
            Toast.makeText(context, "Removed From Fav", Toast.LENGTH_SHORT).show();
            ImageView v1 = (ImageView)v;
            ListView listView = (ListView)(((View)v1.getParent()).getParent());
            int pos= listView.getPositionForView(v);

            (arrayList.get(pos)).setGold(false);

            ArrayList<Movies> temp = new ArrayList<Movies>();
            if (fav != null) {
                Gson gson = new Gson();
                Movies[] arr = gson.fromJson(fav, Movies[].class);
                if (arr != null)
                    temp = new ArrayList<>(Arrays.asList(arr));

            }
            for(Movies ite :arrayList){
                if(temp.contains(ite))
                    temp.remove(ite);
            }
            ArrayList<Movies> items1ArrayList = temp;
            for (Movies item : arrayList) {
                if (item.getGold()) {

                    //Toast.makeText(SearchResults.this, "Added to favourites", Toast.LENGTH_SHORT).show();
                    items1ArrayList.add(item);

                }

            }
            Gson gson = new Gson();
            String json = gson.toJson(items1ArrayList);

            editor.putString(PREFKEY, json);
            editor.commit();
        }
    }
    }

