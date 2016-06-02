package com.example.moohn.todos;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.moohn.todos.com.example.moohn.todos.contentprovider.TodoContentProvider;
import com.example.moohn.todos.database.TodoTable;

public class TodoDetailFragment extends Fragment implements View.OnClickListener{
    private Spinner mCategory;
    private EditText mSummary;
    private EditText mDescription;
    private Button button;

    public Uri getTodoURI(){
        return getArguments().getParcelable(TodoContentProvider.CONTENT__ITEM_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todo_edit_fragment, container, false);
        mCategory =(Spinner) view.findViewById(R.id.category);
        mSummary = (EditText) view.findViewById(R.id.todo_edit_summary);
        mDescription = (EditText) view.findViewById(R.id.todo_edit_description);
        button = (Button) view.findViewById(R.id.todo_edit_button);
        button.setOnClickListener(this);
        fillData(getTodoURI());
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    protected void fillData(Uri todoUri) {
        String[] projection = { TodoTable.COLUMN_SUMMARY, TodoTable.COLUMN_DESCRIPTION, TodoTable.COLUMN_CATEGORY };
        Cursor cursor =  getActivity().getContentResolver().query(todoUri, projection, null, null, null);

        if(cursor !=  null){
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
            cursor.close();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.todo_edit_button:
                if(TextUtils.isEmpty(mSummary.getText().toString())){
                    makeToast();
                }else {
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                }
        }
    }

    private void makeToast() {
        Toast.makeText(getActivity(), "Please maintain a summary", Toast.LENGTH_LONG).show();
    }
}
