package com.example.moohn.todos;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

public class TodoDetailActivity extends Activity {
    private Spinner mCategory;
    private EditText mSummary;
    private EditText mDescription;
    private Uri todoUri;
    private TodoDetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailFragment = (TodoDetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
        if (detailFragment == null ){
            detailFragment = new TodoDetailFragment();
            Bundle extra = getIntent().getExtras();
            detailFragment.setArguments(extra);
            getFragmentManager().beginTransaction().add(android.R.id.content, detailFragment).commit();

        }
    }
}
