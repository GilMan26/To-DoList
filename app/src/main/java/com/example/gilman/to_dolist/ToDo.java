package com.example.gilman.to_dolist;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

@Entity
public class ToDo {
    @PrimaryKey(autoGenerate = true)
    long id;
    String title;
    String desc;
    String time;
    String date;
    long timeinEpochs;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ToDo(String title, String desc, String date, String time) {
        this.title = title;
        this.desc = desc;
        this.date=date;
        this.time=time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getTimeInEpochs() {
        return timeinEpochs;
    }

    public void setTimeInEpochs(long timeInEpochs) {
        this.timeinEpochs = timeInEpochs;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInEpochs);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        ++month;

        this.date = day + "/" + month + "/" + year;

        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);

        this.time = hour + ":" + min;

    }
}
