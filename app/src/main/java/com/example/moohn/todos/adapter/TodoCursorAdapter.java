package com.example.moohn.todos.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

        ImageView star = (ImageView) view.findViewById(R.id.iconStar);
        TextView label = (TextView) view.findViewById(R.id.label);

        Bitmap oriStar = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.reminder);

        Bitmap resizedBitmap =
                Bitmap.createScaledBitmap(oriStar, 10, 10, false);

        star.setImageBitmap(resizedBitmap);
        label.setText(cursor.getString(cursor.getColumnIndexOrThrow(TodoTable.COLUMN_SUMMARY)));
    }
}
