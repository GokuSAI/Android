package com.example.saikrishna.tripsapp;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saikrishna on 12/3/17.
 */

public class Places {

    String name,place,id;
    String areas;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
    }

    @Override
    public String toString() {
        return "Places{" +
                "name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", id='" + id + '\'' +
                ", areas=" + areas +
                '}';
    }


}


class area{
    String id,areaname,url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
