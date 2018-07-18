package com.example.gilman.to_dolist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends ArrayAdapter {

    List<ToDo> items;
    LayoutInflater inflater ;


    public ToDoAdapter(@NonNull Context context, @NonNull List<ToDo> items) {
        super(context, 0, items);
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items=items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View output=inflater.inflate(R.layout.list_item3, parent, false);
        TextView title=output.findViewById(R.id.titleTextView);
        TextView date=output.findViewById(R.id.dateTextView);
        TextView time=output.findViewById(R.id.timeTextView);
        ToDo item=items.get(position);
        title.setText(item.getTitle());
        date.setText(item.getDate());
        time.setText(item.getTime());
        return output;
    }
}
