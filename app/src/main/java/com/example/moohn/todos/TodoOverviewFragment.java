package com.example.moohn.todos;

import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.moohn.todos.adapter.TodoCursorAdapter;
import com.example.moohn.todos.com.example.moohn.todos.contentprovider.TodoContentProvider;
import com.example.moohn.todos.database.TodoTable;

public class TodoOverviewFragment extends ListFragment  implements LoaderManager.LoaderCallbacks<Cursor>{

    private TodoCursorAdapter todoAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.todo_list_fragment,container);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection =  {TodoTable.COLUMN_ID, TodoTable.COLUMN_SUMMARY, TodoTable.COLUMN_CATEGORY};
        return new CursorLoader(getActivity(), TodoContentProvider.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        todoAdapter = new TodoCursorAdapter(getActivity(), data);
        setListAdapter(todoAdapter);
        todoAdapter.swapCursor(data);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(getActivity(), TodoDetailActivity.class);
        Uri todoUri = Uri.parse(TodoContentProvider.CONTENT_URI +"/"+ id);
        i.putExtra(TodoContentProvider.CONTENT__ITEM_TYPE,todoUri );
        startActivity(i);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        todoAdapter.swapCursor(null);
    }
}
