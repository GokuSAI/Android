package com.example.saikrishna.homework6;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

import io.realm.RealmObject;

/**
 * Created by saikrishna on 11/3/17.
 */

public class Instructordetails extends RealmObject implements Parcelable{

    String InstructorFirstName,InstructorLastName,InstructorEmail,InstructorWebsite, courseManagerUserName;
    boolean selected;
    byte[] img;

    public Instructordetails() {

    }

    public byte[] getImg() {
        return img;
    }

    @Override
    public String toString() {
        return "Instructordetails{" +
                "InstructorFirstName='" + InstructorFirstName + '\'' +
                ", InstructorLastName='" + InstructorLastName + '\'' +
                ", InstructorEmail='" + InstructorEmail + '\'' +
                ", InstructorWebsite='" + InstructorWebsite + '\'' +
                ", courseManagerUserName='" + courseManagerUserName + '\'' +
                ", img=" + Arrays.toString(img) +
                '}';
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getCourseManagerUserName() {
        return courseManagerUserName;
    }

    public void setCourseManagerUserName(String courseManagerUserName) {
        this.courseManagerUserName = courseManagerUserName;
    }

    public String getInstructorFirstName() {
        return InstructorFirstName;
    }

    public void setInstructorFirstName(String instructorFirstName) {
        InstructorFirstName = instructorFirstName;
    }

    public String getInstructorLastName() {
        return InstructorLastName;
    }

    public void setInstructorLastName(String instructorLastName) {
        InstructorLastName = instructorLastName;
    }

    public String getInstructorEmail() {
        return InstructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        InstructorEmail = instructorEmail;
    }

    public String getInstructorWebsite() {
        return InstructorWebsite;
    }

    public void setInstructorWebsite(String instructorWebsite) {
        InstructorWebsite = instructorWebsite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        Instructordetails that = (Instructordetails) o;

        if (InstructorFirstName != null ? !InstructorFirstName.equals(that.InstructorFirstName) : that.InstructorFirstName != null)
            return false;
        if (InstructorLastName != null ? !InstructorLastName.equals(that.InstructorLastName) : that.InstructorLastName != null)
            return false;
        if (InstructorEmail != null ? !InstructorEmail.equals(that.InstructorEmail) : that.InstructorEmail != null)
            return false;
        if (InstructorWebsite != null ? !InstructorWebsite.equals(that.InstructorWebsite) : that.InstructorWebsite != null)
            return false;
        return courseManagerUserName != null ? courseManagerUserName.equals(that.courseManagerUserName) : that.courseManagerUserName == null;

    }

    @Override
    public int hashCode() {
        int result = InstructorFirstName != null ? InstructorFirstName.hashCode() : 0;
        result = 31 * result + (InstructorLastName != null ? InstructorLastName.hashCode() : 0);
        result = 31 * result + (InstructorEmail != null ? InstructorEmail.hashCode() : 0);
        result = 31 * result + (InstructorWebsite != null ? InstructorWebsite.hashCode() : 0);
        result = 31 * result + (courseManagerUserName != null ? courseManagerUserName.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(InstructorFirstName);
        dest.writeString(InstructorLastName);
        dest.writeString(InstructorEmail);
        dest.writeString(InstructorWebsite);
        dest.writeString(courseManagerUserName);
    }
    public static final Creator<Instructordetails> CREATOR
            = new Creator<Instructordetails>() {
        public Instructordetails createFromParcel(Parcel in) {
            return new Instructordetails(in);
        }

        public Instructordetails[] newArray(int size) {
            return new Instructordetails[size];
        }
    };

    Instructordetails(Parcel in) {
        InstructorFirstName= in.readString();
InstructorLastName=in.readString();
        InstructorEmail=in.readString();
        InstructorWebsite=in.readString();
        courseManagerUserName=in.readString();


    }
}
