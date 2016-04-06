package com.example.moohn.todos.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moohn.todos.R;
import com.example.moohn.todos.database.TodoTable;

public class TodoCursorAdapter extends CursorAdapter{


    private Context mContext;

    public TodoCursorAdapter(Context context, Cursor c ) {
        super(context, c, 0);
        this.mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.todo_row,parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ImageView imgPriority = (ImageView) view.findViewById(R.id.priority);
        TextView label = (TextView) view.findViewById(R.id.label);

        String priority = cursor.getString(cursor.getColumnIndexOrThrow(TodoTable.COLUMN_CATEGORY));

        String[] priorities =  mContext.getResources().getStringArray(R.array.priorities);

        if (priority.equals("Urgent")){
            imgPriority.setImageResource(R.mipmap.urgent);
        }else {
            imgPriority.setImageResource(R.mipmap.reminder);
        }

        label.setText(cursor.getString(cursor.getColumnIndexOrThrow(TodoTable.COLUMN_SUMMARY)));
    }
}
