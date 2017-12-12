package com.example.app.homework05;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jerin on 10/13/2017.
 */

public class Track implements Parcelable {
    String name,artist,url,smallImageURL,largeImageURL;
    boolean favorite;
    JSONArray image;
    JSONObject artistObj;

    public static Track createTrack(JSONObject js) throws JSONException {
        Track track = new Track();
        track.setName(js.getString("name"));
        track.setArtist(js.getString("artist"));
        track.setUrl(js.getString("url"));
        track.setImage(js.getJSONArray("image"));
        track.setURLs();
        return track;
    }

    public static Track createSimilarTrack(JSONObject js) throws JSONException {
        Track track = new Track();
        track.setName(js.getString("name"));
        track.setArtistObj(js.getJSONObject("artist"));
        track.setUrl(js.getString("url"));
        track.setImage(js.getJSONArray("image"));
        track.setURLs();
        return track;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getUrl() {
        return url;
    }

    public String getSmallImageURL() {
        return smallImageURL;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public JSONArray getImage() {
        return image;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setArtistObj(JSONObject js) throws JSONException {
        artist = js.getString("name");

    }

    public void setURLs() throws JSONException {
        JSONArray arr = getImage();
        String size = new String();
        for(int i=0;i<arr.length();i++){
            JSONObject obj = arr.getJSONObject(i);
            size = obj.getString("size");
            if(size.equals("small"))
                smallImageURL = obj.getString("#text");
            if(size.equals("large"))
                largeImageURL = obj.getString("#text");
        }
    }

    public void setImage(JSONArray image) {
        this.image = image;
    }

    public static final Parcelable.Creator<Track> CREATOR
            = new Parcelable.Creator<Track>() {
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
    private Track(Parcel in) {
        this.name = in.readString();
        this.artist = in.readString();
        this.url = in.readString();
        this.smallImageURL = in.readString();
        this.largeImageURL = in.readString();
    }
    public Track(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(artist);
        dest.writeString(url);
        dest.writeString(smallImageURL);
        dest.writeString(largeImageURL);
    }

    @Override
    public int hashCode() {
        int result =  smallImageURL!= null ? smallImageURL.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (artist != null ? artist.hashCode() : 0);
        result = 31 * result + (largeImageURL != null ? largeImageURL.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track items1 = (Track) o;

        if (smallImageURL != null ? !smallImageURL.equals(items1.smallImageURL) : items1.smallImageURL != null) return false;
        if (name != null ? !name.equals(items1.name) : items1.name != null)
            return false;
        if (artist != null ? !artist.equals(items1.artist) : items1.artist != null) return false;
        return largeImageURL != null ? largeImageURL.equals(items1.largeImageURL) : items1.largeImageURL == null;

    }
}
