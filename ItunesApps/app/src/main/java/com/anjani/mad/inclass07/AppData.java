package com.anjani.mad.inclass07;

import android.util.Log;

/**
 * Created by Anjani Reddy on 23-10-2017.
 */

public class AppData {


    String image,appName,appPrice;
    String limage;

    public String getLimage() {
        return limage;
    }

    public void setLimage(String limage) {
        this.limage = limage;
    }

    public AppData(String image, String appName, String appPrice) {
        this.image = image;
        this.appName = appName;
        this.appPrice = appPrice;
    }

    public AppData()
    {

    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPrice() {
        return appPrice;
    }

    public void setAppPrice(String appPrice) {
        this.appPrice = appPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppData)) return false;

        AppData appData = (AppData) o;

        if (!getAppName().equals(appData.getAppName())) return false;
        if (!getAppPrice().equals(appData.getAppPrice())) return false;
        return  true;

    }

    @Override
    public int hashCode() {
        int result = getImage().hashCode();
        result = 31 * result + getAppName().hashCode();
        result = 31 * result + getAppPrice().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AppData1{" +
                "image='" + image + '\'' +
                ", appName='" + appName + '\'' +
                ", appPrice='" + appPrice + '\'' +
                '}';

    }
}
