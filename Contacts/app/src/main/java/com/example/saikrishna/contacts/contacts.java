package com.example.saikrishna.contacts;

import android.app.ProgressDialog;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by saikrishna on 9/15/17.
 */

public class contacts implements Serializable {
    String imagePath;
    String FirstName;
    String LastName;
    String Company;
    String Phone;
    String Email;
    String URL;
    String Adress;
    String Birthday;
    String NickName;
    String FacebookURL;
    String TwitterURL;
    String Skype;
    String Youtube;

    public contacts( String imagePath, String firstName, String lastName, String company, String phone, String email, String URL, String adress, String birthday, String nickName, String facebookURL, String twitterURL, String skype, String youtube) {
        this.imagePath = imagePath;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Company = company;
        this.Phone = phone;
        this.Email = email;
        this.URL = URL;
        this.Adress = adress;
        this.Birthday = birthday;
        this.NickName = nickName;
        this.FacebookURL = facebookURL;
        this.TwitterURL = twitterURL;
        this.Skype = skype;
        this.Youtube = youtube;
    }



    @Override
    public String toString() {
        return "contacts{" +
                "imagePath='" + imagePath + '\''+
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Company='" + Company + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Email='" + Email + '\'' +
                ", URL='" + URL + '\'' +
                ", Adress='" + Adress + '\'' +
                ", Birthday='" + Birthday + '\'' +
                ", NickName='" + NickName + '\'' +
                ", FacebookURL='" + FacebookURL + '\'' +
                ", TwitterURL='" + TwitterURL + '\'' +
                ", Skype='" + Skype + '\'' +
                ", Youtube='" + Youtube + '\'' +
                '}';
    }
    /*
    public static final Creator<contacts> CREATOR = new Creator<contacts>() {
        @Override
        public contacts createFromParcel(Parcel in) {
            return new contacts(in);
        }

        @Override
        public contacts[] newArray(int size) {
            return new contacts[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
                
        dest.writeString(FirstName);
        dest.writeString(LastName);
        dest.writeString(Company);
        dest.writeString(Phone);
        dest.writeString(Email);
        dest.writeString(URL);
        dest.writeString(Adress);
        dest.writeString(Birthday);
        dest.writeString(NickName);
        dest.writeString(FacebookURL);
        dest.writeString(TwitterURL);
        dest.writeString(Skype);
        dest.writeString(Youtube);

    }*/
}






