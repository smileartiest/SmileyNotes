package com.smilearts.smilenotes.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes")
public class NotesModel {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int Id;

    @ColumnInfo(name = "Title")
    private String Title;
    @ColumnInfo(name = "Message")
    private String Message;
    @ColumnInfo(name = "Date")
    private String Date;
    @ColumnInfo(name = "Time")
    private String Time;
    @ColumnInfo(name = "Bg")
    private String Bg;
    @ColumnInfo(name = "Priority")
    private int Priority;

    public NotesModel() {
    }

    public NotesModel(int id, String title, String message, String date, String time, String bg, int priority) {
        Id = id;
        Title = title;
        Message = message;
        Date = date;
        Time = time;
        Bg = bg;
        Priority = priority;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getBg() {
        return Bg;
    }

    public void setBg(String bg) {
        Bg = bg;
    }

    public int getPriority() {
        return Priority;
    }

    public void setPriority(int priority) {
        Priority = priority;
    }
}
