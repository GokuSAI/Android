package com.example.saikrishna.gsignin;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by saikrishna on 11/18/17.
 */

public class Profile implements Serializable {

    String firstname, lastname, email, password, id;

    String phone,dept,img;
    String birthday,confirmpassword;

    ArrayList<String> friendlist;
    ArrayList<String> sentList;
    ArrayList<String> recievedList;

    public Profile() {
        friendlist = new ArrayList<>();
        sentList = new ArrayList<>();
        recievedList = new ArrayList<>();
    }

    public ArrayList<String> getFriendlist() {
        return friendlist;
    }

    public void setFriendlist(ArrayList<String> friendlist) {
        this.friendlist = friendlist;
    }

    public ArrayList<String> getSentList() {
        return sentList;
    }

    public void setSentList(ArrayList<String> sentList) {
        this.sentList = sentList;
    }

    public ArrayList<String> getRecievedList() {
        return recievedList;
    }

    public void setRecievedList(ArrayList<String> recievedList) {
        this.recievedList = recievedList;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }


    @Override
    public String toString() {
        return "Profile{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", dept='" + dept + '\'' +
                ", img='" + img + '\'' +
                ", birthday='" + birthday + '\'' +
                ", confirmpassword='" + confirmpassword + '\'' +
                ", friendlist=" + friendlist +
                ", sentList=" + sentList +
                ", recievedList=" + recievedList +
                '}';
    }
}
