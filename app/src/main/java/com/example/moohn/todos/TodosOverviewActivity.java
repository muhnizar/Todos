package com.example.moohn.todos;

import android.app.ListActivity;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.LoaderManager;
import android.widget.SimpleCursorAdapter;

import com.example.moohn.todos.database.TodoTable;

public class TodosOverviewActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list);
        this.getListView().setDividerHeight(2);
        fillData();
        registerForContextMenu(getListView());
    }

    private void fillData() {
        String[] from = new String[]{TodoTable.COLUMN_SUMMARY};
        int[] to = new int[]{R.id.label};
        getLoaderManager().initLoader(0, null, this);
        adapter = new SimpleCursorAdapter(this, R.layout.todo_row, null, from,to, 0);

        setListAdapter(adapter);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
