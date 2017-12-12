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
 * Created by user on 10/15/2017.
 */

public class FavoriteAdapter extends ArrayAdapter<Track> {
    List<Track> mData;
    Context context;
    int mResource;
    MySharedPreference preference;

    public FavoriteAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Track> objects) {
        super(context, resource, objects);
        this.context = context;
        this.mData = objects;
        this.mResource = resource;
        preference = new MySharedPreference(context);
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
            track.setFavorite(true);
            star.setImageResource(android.R.drawable.btn_star_big_on);
            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    preference.removeTrack(track);
                    mData.remove(track);
                    notifyDataSetChanged();
                    track.setFavorite(false);
                    Toast.makeText(context, "Removed from Favorite", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return convertView;

    }
}
