package com.example.gilman.to_dolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {
    long id;
    Intent intent;
    int day, month, year, hour, min;
    ToDoDatabase database;
    ToDoDAO toDoDAO;
    long epochtime=0;
    EditText titleEditText , descEditText, dateEditText, timeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        titleEditText=findViewById(R.id.titleET);
        descEditText=findViewById(R.id.descET);
        dateEditText=findViewById(R.id.DateET);
        timeEditText=findViewById(R.id.TimeET);
        intent=getIntent();
        id=intent.getLongExtra("id", 0);
        database = Room.databaseBuilder(getApplicationContext(),ToDoDatabase.class, "ToDo_db").allowMainThreadQueries().build();
        toDoDAO = database.getToDoDAO();
        ToDo toDo=toDoDAO.getToDo(id);
        titleEditText.setHint(toDo.getTitle());
        descEditText.setHint(toDo.getDesc());
        dateEditText.setHint(toDo.getDate());
        timeEditText.setHint(toDo.getTime());

        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                min = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(EditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

//                        timeEditText.setText(hourOfDay + ":" + minute);
//
//                        hour = hourOfDay;
//                        min = minute;

                        int mHour=hourOfDay;
                        int mMinute=minute;
                        if(mHour<10&&mMinute<10){
                            timeEditText.setText("0"+mHour+":"+"0"+mMinute);
                        }
                        else
                        if(mHour<10&&mMinute>10){
                            timeEditText.setText("0"+mHour+":"+mMinute);
                        }
                        else
                        if(mHour>10&&mMinute<10){
                            timeEditText.setText(mHour+":"+"0"+mMinute);
                        }
                        else
                            timeEditText.setText(mHour+":"+mMinute);

                    }
                },hour,min,false);

                timePickerDialog.show();

            }
        });

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                showDatePicker(EditActivity.this, year, month, day);

            }
        });

        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day,hour,min);
        epochtime=calendar.getTimeInMillis();


    }

    public void saveToDo(View view){
        String title=titleEditText.getText().toString();
        String desc=descEditText.getText().toString();
        String date=dateEditText.getText().toString();
        String time=timeEditText.getText().toString();
        ToDo toDo=new ToDo(title, desc, date, time);
        toDoDAO.updateToDo(toDo);
        intent.putExtra("id", toDo.getId());
        setResult(111, intent);
        finish();

//        ToDo toDo=new ToDo(title, desc);
//        toDoDAO.addToDo(toDo);
//        Intent saveIntent=new Intent(ge)
//        setResult(911, intent);
//        finish();
    }

    public void showDatePicker(Context context, int initialYear, int initialMonth, int initialDay) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int year, int month, int day) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);
                        epochtime = calendar.getTime().getTime();
                        dateEditText.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, initialYear, initialMonth, initialDay);
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();

    }

}
