package com.example.gilman.to_dolist;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
@Dao
public interface ToDoDAO {

    @Insert
    void addToDo(ToDo toDo);

    @Delete
    void deleteToDo(ToDo toDo);

    @Update
    void updateToDo(ToDo toDo);

    @Query("SELECT * FROM ToDo WHERE id =:id")
    ToDo getToDo(long id);

    @Query("SELECT * FROM ToDo")
    List<ToDo> getToDos();

    @Query("SELECT MAX(ID) FROM ToDo")
    int getmaxId();

}
