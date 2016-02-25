package com.example.moohn.todos.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TodoTable {

//    database table
    public static final String TABLE_TODO = "todo";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_SUMMARY = "summary";
    public static final String COLUMN_DESCRIPTION = "description";

//    creation sql statement
    public static String DATABASE_CREATE = "create table " + TABLE_TODO
        +"("
        +COLUMN_ID +" integer primary key autoincrement,"
        +COLUMN_CATEGORY + " text not null, "
        +COLUMN_SUMMARY + " text not null, "
        +COLUMN_DESCRIPTION + " text not nul); ";

    public static void oncreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    public static void onUpdate(SQLiteDatabase sqLiteDatabase, int oldversion, int newversion){
        Log.w(TodoTable.class.getName(),"Update database from version "+oldversion +" to version " +newversion);
        sqLiteDatabase.execSQL("DROP IF EXIST "+ TABLE_TODO);
        oncreate(sqLiteDatabase);
    }
}