package com.example.gilman.to_dolist;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.Serializable;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    EditText titleEditText , descEditText,  timeEditText,dateEditText;
    ToDoDatabase database;
    ToDoDAO toDoDAO;
    Button addButton;
    int day, month, year, hour, min;
    long epochtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        database = Room.databaseBuilder(getApplicationContext(),ToDoDatabase.class, "ToDo_db").allowMainThreadQueries().build();
        toDoDAO = database.getToDoDAO();
        Intent intent=getIntent();
        titleEditText=findViewById(R.id.titleET);
        descEditText=findViewById(R.id.descET);
        dateEditText=findViewById(R.id.DateET);
        timeEditText=findViewById(R.id.TimeET);
        addButton=findViewById(R.id.addButton);
        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                min = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                showDatePicker(AddActivity.this, year, month, day);
//                DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//
//                        int tempMonth = month+1;
//                        dateEditText.setText(dayOfMonth + "/" + tempMonth + "/" + year);
//
//                        day = dayOfMonth;
//                        month  = month;
//                        year  = year;
//
//                    }
//                },year,month,day);
//
//                datePickerDialog.show();
            }
        });
//
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day,hour,min);
        epochtime=calendar.getTimeInMillis();


    }

    public void addToDo(View view){
        String title = titleEditText.getText().toString();
        String desc = descEditText.getText().toString();
        String date=dateEditText.getText().toString();
        String time=timeEditText.getText().toString();
        Intent data = new Intent(this, MainActivity.class);
        data.putExtra("title", title);
        data.putExtra("description", desc);
        data.putExtra("date", date);
        data.putExtra("time", time);
        ToDo toDo=new ToDo(title, desc, date, time);
        toDoDAO.addToDo(toDo);
        setResult(99, data);
        finish();

        Intent i = new Intent(AddActivity.this,AlarmReciever.class);

        i.putExtra("title",title);
        i.putExtra("description",desc);

        PendingIntent intent = PendingIntent.getBroadcast(AddActivity.this,1,i,0);
        Calendar calendar=Calendar.getInstance();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        calendar.set(year,month-1,day,hour,min);
        long time1 = calendar.getTimeInMillis();
        manager.set(AlarmManager.RTC_WAKEUP,time1,intent);
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
