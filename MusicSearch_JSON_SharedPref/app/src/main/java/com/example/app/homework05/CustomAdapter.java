package com.example.app.homework05;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerin on 10/14/2017.
 */

public class CustomAdapter extends ArrayAdapter<Track> {
    List<Track> mData;
    Context context;
    int mResource;
    MySharedPreference preference;
    ArrayList<Track> favoriteTrackList = new ArrayList<>();
    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Track> objects) {
        super(context, resource, objects);
        this.context = context;
        this.mData = objects;
        this.mResource = resource;
        preference = new MySharedPreference(context);
        favoriteTrackList = preference.getItemList();
        if(favoriteTrackList==null){
            favoriteTrackList = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
        }
        if(mData!=null) {
            final Track track = mData.get(position);

            TextView name = (TextView) convertView.findViewById(R.id.trackName);
            name.setText(track.getName());

            TextView artist = (TextView) convertView.findViewById(R.id.artistName);
            artist.setText(track.getArtist());

            ImageView img = (ImageView) convertView.findViewById(R.id.image);
            if (!track.getLargeImageURL().equals(""))
                Picasso.with(context).load(track.getSmallImageURL()).into(img);
            else
                img.setImageResource(R.drawable.default_image);

            final ImageView star = (ImageView) convertView.findViewById(R.id.star);
            if (track.isFavorite()) {
                star.setImageResource(android.R.drawable.btn_star_big_on);
                star.setTag("Gold");
            } else {
                star.setImageResource(android.R.drawable.btn_star_big_off);
                star.setTag("Silver");
            }
        /*if(favoriteTrackList!=null && favoriteTrackList.contains(track)){
            track.setFavorite(true);
            notifyDataSetChanged();
        }*/
            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!track.isFavorite() && favoriteTrackList.size()<20) {
                        star.setImageResource(android.R.drawable.btn_star_big_on);
                        track.setFavorite(true);
                        favoriteTrackList.add(track);
                        preference.addTrack(track);
                        Toast.makeText(context, "Added to Favorite", Toast.LENGTH_SHORT).show();
                    }
                    else if(!track.isFavorite() && favoriteTrackList.size()>=20){
                        Toast.makeText(context, "Favorites limit reached", Toast.LENGTH_SHORT).show();
                    } else{
                        star.setImageResource(android.R.drawable.btn_star_big_off);
                        track.setFavorite(false);
                        preference.removeTrack(track);
                        favoriteTrackList.remove(track);
                        Toast.makeText(context, "Removed from Favorite", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }

        return convertView;
    }
}

