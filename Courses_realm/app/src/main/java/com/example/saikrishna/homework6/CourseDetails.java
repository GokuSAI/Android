package com.example.saikrishna.homework6;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

import io.realm.RealmObject;

/**
 * Created by saikrishna on 11/4/17.
 */

public class CourseDetails extends RealmObject implements Parcelable{
    String titleofcourse,nameofinstructor,day,time,semester,credithours,CourseManagerUserName;
    byte[] img;

    public CourseDetails() {
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getCourseManagerUserName() {
        return CourseManagerUserName;
    }

    public void setCourseManagerUserName(String courseManagerUserName) {
        CourseManagerUserName = courseManagerUserName;
    }

    /*@Override
    public String toString() {
        return "CourseDetails{" +
                "titleofcourse='" + titleofcourse + '\'' +
                ", nameofinstructor='" + nameofinstructor + '\'' +
                ", day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", semester='" + semester + '\'' +
                ", credithours='" + credithours + '\'' +
                ", CourseManagerUserName='" + CourseManagerUserName + '\'' +
                ", img=" + Arrays.toString(img) +
                '}';
    }*/

    public String getTitleofcourse() {
        return titleofcourse;
    }

    public void setTitleofcourse(String titleofcourse) {
        this.titleofcourse = titleofcourse;
    }

    public String getNameofinstructor() {
        return nameofinstructor;
    }

    public void setNameofinstructor(String nameofinstructor) {
        this.nameofinstructor = nameofinstructor;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCredithours() {
        return credithours;
    }

    public void setCredithours(String credithours) {
        this.credithours = credithours;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titleofcourse);
        dest.writeString(nameofinstructor);
        dest.writeString(day);
        dest.writeString(time);
        dest.writeString(semester);
        dest.writeString(credithours);
        dest.writeString(CourseManagerUserName);
    }
    public static final Creator<CourseDetails> CREATOR
            = new Creator<CourseDetails>() {
        public CourseDetails createFromParcel(Parcel in) {
            return new CourseDetails(in);
        }

        public CourseDetails[] newArray(int size) {
            return new CourseDetails[size];
        }
    };

    private CourseDetails(Parcel in) {
        titleofcourse = in.readString();
        nameofinstructor=in.readString();
        day=in.readString();
        time=in.readString();
        semester=in.readString();
        credithours=in.readString();
        CourseManagerUserName=in.readString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        //if (o == null || getClass() != o.getClass()) return false;

        CourseDetails that = (CourseDetails) o;

        if (titleofcourse != null ? !titleofcourse.equals(that.titleofcourse) : that.titleofcourse != null)
            return false;
        if (nameofinstructor != null ? !nameofinstructor.equals(that.nameofinstructor) : that.nameofinstructor != null)
            return false;
        if (day != null ? !day.equals(that.day) : that.day != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (semester != null ? !semester.equals(that.semester) : that.semester != null)
            return false;
        if (credithours != null ? !credithours.equals(that.credithours) : that.credithours != null)
            return false;
        if (CourseManagerUserName != null ? !CourseManagerUserName.equals(that.CourseManagerUserName) : that.CourseManagerUserName != null)
            return false;
        return Arrays.equals(img, that.img);

    }

    @Override
    public int hashCode() {
        int result = titleofcourse != null ? titleofcourse.hashCode() : 0;
        result = 31 * result + (nameofinstructor != null ? nameofinstructor.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (semester != null ? semester.hashCode() : 0);
        result = 31 * result + (credithours != null ? credithours.hashCode() : 0);
        result = 31 * result + (CourseManagerUserName != null ? CourseManagerUserName.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(img);
        return result;
    }
}
