package com.example.saikrishna.tripsapp;

/**
 * Created by saikrishna on 12/4/17.
 */

public class NearbyCities {
    String description,placeid;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlaceid() {
        return placeid;
    }

    public void setPlaceid(String placeid) {
        this.placeid = placeid;
    }

    @Override
    public String toString() {
        return "NearbyCities{" +
                "description='" + description + '\'' +
                ", placeid='" + placeid + '\'' +
                '}';
    }
}
