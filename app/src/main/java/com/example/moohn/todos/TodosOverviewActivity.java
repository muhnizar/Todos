package com.example.moohn.todos;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.moohn.todos.adapter.TodoCursorAdapter;
import com.example.moohn.todos.com.example.moohn.todos.contentprovider.TodoContentProvider;
import com.example.moohn.todos.database.TodoTable;

public class TodosOverviewActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter adapter;
    private TodoCursorAdapter  todoAdapter;
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
//      init loader
        getLoaderManager().initLoader(0, null, this);

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0,R.string.menu_delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Uri uri = Uri.parse(TodoContentProvider.CONTENT_URI +"/"+info.id);
                getContentResolver().delete(uri,null,null);
                fillData();
            return true;
        }

        return super.onContextItemSelected(item);
    }

    private void createTodo() {
        Intent i = new Intent(this, TodoDetailActivity.class);
        startActivity(i);
    }

    //  immediately called after init loader
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection =  {TodoTable.COLUMN_ID, TodoTable.COLUMN_SUMMARY, TodoTable.COLUMN_CATEGORY};

        return new CursorLoader(this, TodoContentProvider.CONTENT_URI, projection, null, null, null);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, TodoDetailActivity.class);
        Uri todoUri = Uri.parse(TodoContentProvider.CONTENT_URI +"/"+ id);
        i.putExtra(TodoContentProvider.CONTENT__ITEM_TYPE,todoUri );
        startActivity(i);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//    the asynchronous load is complete and data is available now.
//    now we can associate with adapter
        todoAdapter = new TodoCursorAdapter(this, data);
        setListAdapter(todoAdapter);
        todoAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
//      data is now unavailable;
        adapter.swapCursor(null);
    }
}
