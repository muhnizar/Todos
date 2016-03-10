package com.example.moohn.todos;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class TodoDetailActivity extends Activity{
    private Spinner mCategory;
    private EditText mSummary;
    private EditText mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_edit);
        
        mCategory =(Spinner) findViewById(R.id.category);
        mSummary = (EditText) findViewById(R.id.todo_edit_summary);
        mDescription = (EditText) findViewById(R.id.todo_edit_description);
        Button confirmButton = (Button) findViewById(R.id.todo_edit_button);

        Bundle extra = getIntent().getExtras();

    }
}
