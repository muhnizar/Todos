package com.example.moohn.todos;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.SimpleCursorAdapter;

import com.example.moohn.todos.adapter.TodoCursorAdapter;

import java.util.ArrayList;
import java.util.List;

public class TodosOverviewActivity extends FragmentActivity {

    private SimpleCursorAdapter adapter;
    private TodoCursorAdapter  todoAdapter;
    private static final int DELETE_ID = Menu.FIRST + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.todo_list);
        FragmentManager fm = getSupportFragmentManager();
        // Create the list fragment and add it as our sole content.
        if (fm.findFragmentById(R.id.todoListFragment) == null) {
            TodoOverviewFragment listFragment = new TodoOverviewFragment();
            fm.beginTransaction().add(R.id.todoListFragment , listFragment).commit();
        }
    }

    private  void setupViewPager(ViewPager viewPager){
        FragmentManager fm = getSupportFragmentManager();
        TodoOverviewFragment listFragment = null;
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(fm);
        if (fm.findFragmentById(R.id.todoListFragment) == null) {
            listFragment = new TodoOverviewFragment();
            fm.beginTransaction().add(R.id.todoListFragment , listFragment).commit();
        }
        viewPagerAdapter.addFragment(listFragment, "list");
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(getSupportFragmentManager());
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentTitleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
