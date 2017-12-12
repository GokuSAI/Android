package edu.uncc.myfavoritemovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    String name, description, imdb;
    int rating,year;
    Genre genre;

    public Movie(String name, String description, Genre genre, int rating, int year, String imdb) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.rating = rating;
        this.year = year;
        this.imdb = imdb;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getImdb() {
        return imdb;
    }

    public int getRating() {
        return rating;
    }

    public int getYear() {
        return year;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(genre.name());
        dest.writeInt(rating);
        dest.writeInt(year);
        dest.writeString(imdb);

    }

    public static final Creator<Movie> CREATOR
            = new Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private Movie(Parcel in) {
        name = in.readString();
        description=in.readString();
        genre=Genre.valueOf(in.readString());
        rating=in.readInt();
        year=in.readInt();
        imdb=in.readString();
    }

}
