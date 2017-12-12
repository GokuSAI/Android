package edu.uncc.notekeeper;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Sai Yesaswy on 2/26/2017.
 */

public class Note {
    private long id;
    private String taskNote;
    private String status;
    private String priority;
    private Date createdTime;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
