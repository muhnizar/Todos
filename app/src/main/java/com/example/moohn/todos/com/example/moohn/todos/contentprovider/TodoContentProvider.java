package com.example.moohn.todos.com.example.moohn.todos.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.example.moohn.todos.database.TodoDatabaseHelper;
import com.example.moohn.todos.database.TodoTable;

import java.util.Arrays;
import java.util.HashSet;

public class TodoContentProvider extends ContentProvider {

    private TodoDatabaseHelper databaseHelper;

    private TodoDatabaseHelper todoDatabaseHelper;
    private static final int TODOS = 10;
    private static final int TODO_ID = 20;


    private static final String AUTHORITY = "com.example.moohn.todos.contentprovider";
    private static final String BASE_PATH = "todos";
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);




    static{
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, TODOS);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH+"/#", TODO_ID);

    }

    @Override
    public boolean onCreate() {
        todoDatabaseHelper = new TodoDatabaseHelper(getContext());
        return false;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder sqLiteQueryBuilder =  new SQLiteQueryBuilder();

//      check if the caller projection is exist
        checkColumn(projection);

//      set table
        sqLiteQueryBuilder.setTables(TodoTable.TABLE_TODO);

        int uriType = sURIMatcher.match(uri);

        switch (uriType){
            case TODOS:
                break;
            case TODO_ID:
                sqLiteQueryBuilder.appendWhere(TodoTable.COLUMN_ID + "="+uri.getLastPathSegment() );
                break;
            default:
                throw new IllegalArgumentException("unknown uri");

        }


        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Cursor cursor = sqLiteQueryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
//        to make sure potential listener are notified
        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = 0;
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        switch (uriType){
            case TODOS:
                id  = db.insert(TodoTable.TABLE_TODO, null, values);
                break;
            default:
                throw new IllegalArgumentException("unknow uri:" +uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(BASE_PATH +"/"+id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private void checkColumn(String[] projection) {
        String[] available = {TodoTable.COLUMN_ID, TodoTable.COLUMN_SUMMARY, TodoTable.COLUMN_DESCRIPTION, TodoTable.COLUMN_CATEGORY};

        HashSet<String> requestColumns = new HashSet<>(Arrays.asList(projection));
        HashSet<String> availableColumns = new HashSet<>(Arrays.asList(available));

        if(projection!= null){
            if (!availableColumns.containsAll(requestColumns)){
                throw new IllegalArgumentException("Unknown column in projection");
            }
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }


}
