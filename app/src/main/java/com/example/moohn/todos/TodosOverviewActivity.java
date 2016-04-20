package com.example.moohn.todos;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.SimpleCursorAdapter;

import com.example.moohn.todos.adapter.TodoCursorAdapter;

public class TodosOverviewActivity extends FragmentActivity {

    private SimpleCursorAdapter adapter;
    private TodoCursorAdapter  todoAdapter;
    private static final int DELETE_ID = Menu.FIRST + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.todo_list);
        FragmentManager fm = getFragmentManager();
        // Create the list fragment and add it as our sole content.
        if (fm.findFragmentById(R.id.todoListFragment) == null) {
            TodoOverviewFragment listFragment = new TodoOverviewFragment();
            fm.beginTransaction().add(R.id.todoListFragment , listFragment).commit();
        }
    }

}
