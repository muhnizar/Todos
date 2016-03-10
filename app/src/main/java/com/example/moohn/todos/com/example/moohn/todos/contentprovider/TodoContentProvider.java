package com.example.moohn.todos.com.example.moohn.todos.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.moohn.todos.database.TodoDatabaseHelper;
import com.example.moohn.todos.database.TodoTable;

import java.util.Arrays;
import java.util.HashSet;

public class TodoContentProvider extends ContentProvider {

    private TodoDatabaseHelper databaseHelper;

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
        databaseHelper = new TodoDatabaseHelper(getContext());
        return false;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

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

    @SuppressWarnings("ConstantConditions")
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long id;
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

    @SuppressWarnings("ConstantConditions")
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int rowDeleted;
        switch (uriType){
            case TODOS:
               rowDeleted = db.delete(TodoTable.TABLE_TODO, selection, selectionArgs);
               break;
            case TODO_ID:
                String id = uri.getLastPathSegment();
                if(TextUtils.isEmpty(selection)){
                    rowDeleted = db.delete(TodoTable.TABLE_TODO, TodoTable.COLUMN_ID +" = "+id, selectionArgs);
                }else{
                    rowDeleted = db.delete(TodoTable.TABLE_TODO, TodoTable.COLUMN_ID +" = "+id +" and " +selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("unknown uri :" +uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int rowUpdated ;

        switch (uriType){
            case TODOS:
                rowUpdated = db.update(TodoTable.TABLE_TODO, values, selection, selectionArgs);
                break;
            case TODO_ID:
                String id = uri.getLastPathSegment();
                if(TextUtils.isEmpty(selection)){
                    rowUpdated = db.update(TodoTable.TABLE_TODO, values, TodoTable.COLUMN_ID +" = "+id, selectionArgs);
                }else{
                    rowUpdated = db.update(TodoTable.TABLE_TODO, values,  TodoTable.COLUMN_ID +" = "+id +" and "+ selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown uri:"+uri);

        }
        return rowUpdated;
    }

    private void checkColumn(String[] projection) {
        String[] available = {TodoTable.COLUMN_ID, TodoTable.COLUMN_SUMMARY, TodoTable.COLUMN_DESCRIPTION, TodoTable.COLUMN_CATEGORY};

        if (projection != null) {
            HashSet<String> requestColumns = new HashSet<>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<>(Arrays.asList(available));

            if (!availableColumns.containsAll(requestColumns)) {
                throw new IllegalArgumentException("Unknown column in projection");
            }
        }
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
