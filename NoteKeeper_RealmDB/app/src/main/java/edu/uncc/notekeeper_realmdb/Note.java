package edu.uncc.notekeeper_realmdb;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import static android.R.attr.id;

/*
Assignment InClass13
Note.java
Sai Yesaswy Mylavarapu
 */


public class Note extends RealmObject{
    @PrimaryKey
    int note_id;

    private String taskNote;
    private String status;
    private String priority;
    private Date createdTime;
    private int priorityId;

    public int getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(int priorityId) {
        this.priorityId = priorityId;
    }

    @Override
    public String toString() {
        return "Note{" +
                "createdTime=" + createdTime +
                ", id=" + id +
                ", taskNote='" + taskNote + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Note(String taskNote, String status, String priority, Date createdTime) {
        this.taskNote = taskNote;
        this.status = status;
        this.priority=priority;
        this.createdTime=createdTime;
    }

    public Note(){

    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.note_id = id;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTaskNote() {
        return taskNote;
    }

    public void setTaskNote(String taskNote) {
        this.taskNote = taskNote;
    }

}
