package com.example.moohn.todos;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.moohn.todos.com.example.moohn.todos.contentprovider.TodoContentProvider;
import com.example.moohn.todos.database.TodoTable;

public class TodoDetailActivity extends Activity{
    private Spinner mCategory;
    private EditText mSummary;
    private EditText mDescription;
    private Uri todoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_edit);
        
        mCategory =(Spinner) findViewById(R.id.category);
        mSummary = (EditText) findViewById(R.id.todo_edit_summary);
        mDescription = (EditText) findViewById(R.id.todo_edit_description);
        Button confirmButton = (Button) findViewById(R.id.todo_edit_button);

        Bundle extra = getIntent().getExtras();

        if (extra != null ){
            todoUri = extra.getParcelable(TodoContentProvider.CONTENT__ITEM_TYPE);
            fillData(todoUri);
        }

        confirmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mSummary.getText().toString())){
                    makeToast();
                }else {
                    setResult(RESULT_OK);
                    finish();
                }

            }
        });


    }

    private void fillData(Uri todoUri) {
        String[] projection = { TodoTable.COLUMN_SUMMARY, TodoTable.COLUMN_DESCRIPTION, TodoTable.COLUMN_CATEGORY };
        Cursor cursor = getContentResolver().query(todoUri, projection, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
            String category = cursor.getString(cursor.getColumnIndexOrThrow(TodoTable.COLUMN_CATEGORY));
            for (int i=0;i<mCategory.getCount();i++){
                String item = (String) mCategory.getItemAtPosition(i);
                if (item.equalsIgnoreCase(category)){
                    mCategory.setSelection(i);
                }
            }
            mSummary.setText(cursor.getString(cursor.getColumnIndexOrThrow(TodoTable.COLUMN_SUMMARY)));
            mDescription.setText(cursor.getString(cursor.getColumnIndexOrThrow(TodoTable.COLUMN_DESCRIPTION)));
//    dont forfet to close the cursor
            cursor.close();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putParcelable(TodoContentProvider.CONTENT__ITEM_TYPE,todoUri);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    private void saveState(){
        String category = (String) mCategory.getSelectedItem();
        String summary = mSummary.getText().toString();
        String description = mDescription.getText().toString();

        ContentValues values =  new ContentValues();
        values.put(TodoTable.COLUMN_CATEGORY, category);
        values.put(TodoTable.COLUMN_SUMMARY, summary);
        values.put(TodoTable.COLUMN_DESCRIPTION, description);

        if (todoUri == null){
            getContentResolver().insert(TodoContentProvider.CONTENT_URI, values );
        }else{
            getContentResolver().update(todoUri, values, null, null);
        }
    }

    private void makeToast() {
        Toast.makeText(TodoDetailActivity.this, "Please maintain a summary", Toast.LENGTH_LONG).show();
    }

}
