package com.example.gilman.to_dolist;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    List<ToDo> items=new ArrayList<>();
    ToDoDAO toDoDAO;
    ToDoAdapter adapter;
//    int day, month, year, hour, min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView list=findViewById(R.id.list);
        if( !(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) ){

            String[] permissions = {Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS};
            ActivityCompat.requestPermissions(this,permissions,1011);
        }

        ToDoDatabase database=Room.databaseBuilder(getApplicationContext(),ToDoDatabase.class, "ToDo_db").allowMainThreadQueries().build();
        toDoDAO=database.getToDoDAO();
        items=toDoDAO.getToDos();
        adapter=new ToDoAdapter(this, items);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);
        BroadcastReceiver receiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                refresh();
            }
        };
    }

    public void refresh(){
        int id=toDoDAO.getmaxId();
        ToDo toDo=toDoDAO.getToDo(id);
        items.add(toDo);
        adapter.notifyDataSetChanged();
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.addMenu){
            Intent intent = new Intent(this, AddActivity.class);
            startActivityForResult(intent, 1010);
        }
        return true;
        //return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1010){
            if(resultCode==99){
//                ToDo addtd= (ToDo) data.getSerializableExtra("newToDo");
//                toDoDAO.addToDo(addtd);
//                items.add(addtd);
//                adapter.notifyDataSetChanged();
                String title=data.getStringExtra("title");
                String desc=data.getStringExtra("description");
                String date=data.getStringExtra("date");
                String time=data.getStringExtra("time");
                ToDo toDo=new ToDo(title, desc, date, time);
                items.add(toDo);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        final ToDo toDo=items.get(i);
        final int position=i;
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this task ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                TaskOpenHelper openHelper=TaskOpenHelper.getInstance(getApplicationContext());
//                SQLiteDatabase database=openHelper.getWritableDatabase();
//                long id=task.getId();
//                String[] selectionArgs={id+""};
//                database.delete(Contract.tasks.TABLE_NAME, Contract.tasks.COL_ID+" = ?", selectionArgs);
//                tasks.remove(position);
//                adaptor.notifyDataSetChanged();
                //Toast.makeText(this, "Entry Deleted", Toast.LENGTH_LONG).show();
                toDoDAO.deleteToDo(toDo);
                items.remove(position);
                adapter.notifyDataSetChanged();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ToDo todo=items.get(i);
        Intent intent=new Intent(this, OpenActivity.class);
        intent.putExtra("id", todo.getId());
        startActivity(intent);
    }
}
