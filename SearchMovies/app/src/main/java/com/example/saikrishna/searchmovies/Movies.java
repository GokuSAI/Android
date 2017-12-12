package com.example.saikrishna.searchmovies;

import java.io.Serializable;

/**
 * Created by saikrishna on 10/16/17.
 */

public class Movies implements Serializable {

    String Name,year,imgurl;
    public Boolean isGold;
    String Rating,Popularity,Overview;

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getPopularity() {
        return Popularity;
    }

    public void setPopularity(String popularity) {
        Popularity = popularity;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }


    public Boolean getGold() {
        return isGold;
    }

    public void setGold(Boolean gold) {

        isGold = gold;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "Name='" + Name + '\'' +
                ", year='" + year + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", isGold=" + isGold +
                ", Rating='" + Rating + '\'' +
                ", Popularity='" + Popularity + '\'' +
                ", Overview='" + Overview + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movies movies = (Movies) o;

        if (!Name.equals(movies.Name)) return false;
        if (!year.equals(movies.year)) return false;
        if (!imgurl.equals(movies.imgurl)) return false;
        if (!Rating.equals(movies.Rating)) return false;
        if (!Popularity.equals(movies.Popularity)) return false;
        return Overview.equals(movies.Overview);

    }

    @Override
    public int hashCode() {
        int result = Name.hashCode();
        result = 31 * result + year.hashCode();
        result = 31 * result + imgurl.hashCode();
        result = 31 * result + Rating.hashCode();
        result = 31 * result + Popularity.hashCode();
        result = 31 * result + Overview.hashCode();
        return result;
    }
}
