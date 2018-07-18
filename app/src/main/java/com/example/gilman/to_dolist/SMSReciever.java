package com.example.gilman.to_dolist;

import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReciever extends BroadcastReceiver {
    String title, desc;
    long timestamp;
    ToDoDatabase database;
    ToDoDAO toDoDAO;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data= intent.getExtras();

        if(data!=null){
            Object[] pdus = (Object[]) data.get("pdus");

            for (int i = 0; i < pdus.length; ++i) {

                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i], "3gpp");
                title= smsMessage.getDisplayMessageBody();
                desc = smsMessage.getDisplayMessageBody();
                timestamp = smsMessage.getTimestampMillis();

            }
//            ToDo todo=new ToDo(title, desc);
//            todo.setTimeInEpochs(timestamp);
//            database = Room.databaseBuilder(context.getApplicationContext(),ToDoDatabase.class, "ToDo_db").allowMainThreadQueries().build();
//            toDoDAO = database.getToDoDAO();
//            toDoDAO.addToDo(todo);
//            Toast.makeText(context, "Todo Added!", Toast.LENGTH_SHORT).show();

//            ExpensesOpenHelper expensesOpenHelper = ExpensesOpenHelper.getInstance(context);
//            SQLiteDatabase database = expensesOpenHelper.getWritableDatabase();
//
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(Contract.Expense.COLUMN_NAME, name);
//            contentValues.put(Contract.Expense.COLUMN_AMOUNT, amount);
//            contentValues.put(Contract.Expense.DATE_TIME, expense.getTimeInEpochs());

//            long id = database.insert(Contract.Expense.TABLE_NAME, null, contentValues);
//            if (id > -1L) {
//                Toast.makeText(context, "Todo Added!", Toast.LENGTH_SHORT).show();
//            }
        }
    }
}
