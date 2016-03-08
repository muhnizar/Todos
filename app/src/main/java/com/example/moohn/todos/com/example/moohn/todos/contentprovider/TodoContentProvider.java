package com.example.moohn.todos.com.example.moohn.todos.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.moohn.todos.database.TodoDatabaseHelper;
import com.example.moohn.todos.database.TodoTable;

import java.util.Arrays;
import java.util.HashSet;

public class TodoContentProvider extends ContentProvider {
    private TodoDatabaseHelper todoDatabaseHelper;
    private static final int TODOS = 10;
    private static final int TODO_ID = 20;


    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

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


        sqLiteQueryBuilder.setTables(TodoTable.TABLE_TODO);

//        int uriType = uri.geti
        return null;
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

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
