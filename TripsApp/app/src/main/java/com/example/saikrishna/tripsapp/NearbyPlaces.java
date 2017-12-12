package com.example.saikrishna.tripsapp;

/**
 * Created by saikrishna on 12/4/17.
 */

public class NearbyPlaces {
    String icon,name;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "NearbyPlaces{" +
                "icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
