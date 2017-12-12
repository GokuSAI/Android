package com.example.saikrishna.inclass09;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by saikrishna on 11/7/17.
 */

public class MessageClass implements Serializable {
    String firstname,lastname,userid,msgID,messagecontent;
    String Createdtime;

    public String getMessagecontent() {
        return messagecontent;
    }

    @Override
    public String toString() {
        return "MessageClass{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", userid='" + userid + '\'' +
                ", msgID='" + msgID + '\'' +
                ", Createdtime='" + Createdtime + '\'' +
                ", messagecontent='" + messagecontent + '\'' +
                '}';
    }

    public void setMessagecontent(String messagecontent) {
        this.messagecontent = messagecontent;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public String getCreatedtime() {
        return Createdtime;
    }

    public void setCreatedtime(String createdtime) {
        Createdtime = createdtime;
    }
}
