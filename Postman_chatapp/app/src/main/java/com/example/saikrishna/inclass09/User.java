package com.example.saikrishna.inclass09;

import java.io.Serializable;

/**
 * Created by saikrishna on 11/6/17.
 */

public class User implements Serializable {
    String status,message,data,token,userId,userEmail,userFname,userLname,userRole;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFname() {
        return userFname;
    }

    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

    public String getUserLname() {
        return userLname;
    }

    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public User() {
    }

    public User(String status, String message, String data, String token, String userId, String userEmail, String userFname, String userLname, String userRole) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.token = token;
        this.userId = userId;
        this.userEmail = userEmail;
        this.userFname = userFname;
        this.userLname = userLname;
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                ", token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userFname='" + userFname + '\'' +
                ", userLname='" + userLname + '\'' +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
