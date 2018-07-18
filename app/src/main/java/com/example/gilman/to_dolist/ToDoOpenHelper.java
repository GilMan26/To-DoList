package com.example.gilman.to_dolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoOpenHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME="ToDo_db";
    private static int DATABASE_VER=1;
    private ToDoOpenHelper instance;

    public ToDoOpenHelper getInstance(Context context){
        if(instance==null)
            instance=new ToDoOpenHelper(context);
        return instance;
    }

    public ToDoOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String taskSQL="CREATE TABLE "+Contract.TABLE_NAME+" ("+Contract.COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+Contract.COL_TITLE+" TEXT, "+Contract.COL_DESC+" TEXT)";
        sqLiteDatabase.execSQL(taskSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
