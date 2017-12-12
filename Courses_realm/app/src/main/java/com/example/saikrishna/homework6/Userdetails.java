package com.example.saikrishna.homework6;

import io.realm.RealmObject;

/**
 * Created by saikrishna on 11/2/17.
 */

public class Userdetails extends RealmObject {

 String PersonUsername;
      String PersonFirstname,PersonLastname,PersonPassword;


    public String getPersonUsername() {
        return PersonUsername;
    }

    public void setPersonUsername(String personUsername) {
        PersonUsername = personUsername;
    }

    public String getPersonFirstname() {
        return PersonFirstname;
    }

    public void setPersonFirstname(String personFirstname) {
        PersonFirstname = personFirstname;
    }

    public String getPersonLastname() {
        return PersonLastname;
    }

    public void setPersonLastname(String personLastname) {
        PersonLastname = personLastname;
    }

    public String getPersonPassword() {
        return PersonPassword;
    }

    public void setPersonPassword(String personPassword) {
        PersonPassword = personPassword;
    }

    @Override
    public String toString() {
        return "Userdetails{" +

                ", PersonUsername='" + PersonUsername + '\'' +
                ", PersonFirstname='" + PersonFirstname + '\'' +
                ", PersonLastname='" + PersonLastname + '\'' +
                ", PersonPassword='" + PersonPassword + '\'' +
                '}';
    }
}

