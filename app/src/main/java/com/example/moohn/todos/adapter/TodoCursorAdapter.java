package com.example.moohn.todos.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.example.moohn.todos.R;

public class TodoCursorAdapter extends CursorAdapter{


    public TodoCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.todo_row,parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ImageView star = (ImageView) view.findViewById(R.id.iconStar);
    }
}
