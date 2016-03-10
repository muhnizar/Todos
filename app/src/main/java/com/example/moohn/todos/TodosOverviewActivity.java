package com.example.moohn.todos;

import android.app.ListActivity;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.LoaderManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;

import com.example.moohn.todos.com.example.moohn.todos.contentprovider.TodoContentProvider;
import com.example.moohn.todos.database.TodoTable;

public class TodosOverviewActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter adapter;
    private static final int DELETE_ID = Menu.FIRST + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list);
        this.getListView().setDividerHeight(2);
        fillData();
//        register context menu to the list
        registerForContextMenu(getListView());
    }

    private void fillData() {
        String[] from = new String[]{TodoTable.COLUMN_SUMMARY};
        int[] to = new int[]{R.id.label};
//      init loader
        getLoaderManager().initLoader(0, null, this);
        adapter = new SimpleCursorAdapter(this, R.layout.todo_row, null, from,to, 0);
        setListAdapter(adapter  );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.listmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.insert:
            createTodo();    
            return true;
            
        
        
        }


        return super.onOptionsItemSelected(item);
    }

    private void createTodo() {
        Intent i = new Intent(this, TodoDetailActivity.class);
        startActivity(i);
    }

    //  immediately called after init loader
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection =  {TodoTable.COLUMN_ID, TodoTable.COLUMN_SUMMARY};
        return new CursorLoader(this, TodoContentProvider.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//    the asynchronous load is complete and data is available now.
//    now we can associate with adapter
      adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
//      data is now unavailable;
        adapter.swapCursor(null);
    }
}
