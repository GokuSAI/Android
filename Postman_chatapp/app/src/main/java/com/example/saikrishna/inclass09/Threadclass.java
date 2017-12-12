package com.example.saikrishna.inclass09;

import java.io.Serializable;

/**
 * Created by saikrishna on 11/6/17.
 */

public class Threadclass implements Serializable{

    String firstname,lastname,userid,ID,Title,Createdtime;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCreatedtime() {
        return Createdtime;
    }

    public void setCreatedtime(String createdtime) {
        Createdtime = createdtime;
    }

    @Override
    public String toString() {
        return "Threadclass{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", userid='" + userid + '\'' +
                ", ID='" + ID + '\'' +
                ", Title='" + Title + '\'' +
                ", Createdtime='" + Createdtime + '\'' +
                '}';
    }
}
