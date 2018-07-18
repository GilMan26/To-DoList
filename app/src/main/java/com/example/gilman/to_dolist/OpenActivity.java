package com.example.gilman.to_dolist;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class OpenActivity extends AppCompatActivity {
    long id;
    TextView title, desc, date, time;
    FloatingActionButton fab;
    ToDoDatabase database;
    ToDoDAO toDoDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        database = Room.databaseBuilder(getApplicationContext(),ToDoDatabase.class, "ToDo_db").allowMainThreadQueries().build();
        toDoDAO = database.getToDoDAO();

        title=findViewById(R.id.titleET);
        desc=findViewById(R.id.descET);
        time=findViewById(R.id.DateET);
        date=findViewById(R.id.TimeET);
        Intent intent=getIntent();
        id=intent.getLongExtra("id", 0);
        ToDo toDo=toDoDAO.getToDo(id);
        String stitle=toDo.getTitle();
        String sdesc=toDo.getDesc();
        String sdate=toDo.getDate();
        String stime=toDo.getTime();
        title.setText(stitle);
        desc.setText(sdesc);
        date.setText(sdate);
        time.setText(stime);
    }

    public void editToDo(View view){
        Intent intent=new Intent(getApplicationContext(), EditActivity.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, 1010);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1010){
            if(resultCode==111){
                long id=data.getLongExtra("id", 0);
                ToDo toD0=toDoDAO.getToDo(id);
                title.setText(toD0.getTitle());
                desc.setText(toD0.getDesc());
                date.setText(toD0.getDate());
                time.setText(toD0.getTime());

            }
        }
    }
}
