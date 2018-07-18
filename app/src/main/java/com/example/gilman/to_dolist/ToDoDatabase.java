package com.example.gilman.to_dolist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = ToDo.class , version = 1)
public abstract class ToDoDatabase extends RoomDatabase {
    abstract ToDoDAO getToDoDAO();
}
