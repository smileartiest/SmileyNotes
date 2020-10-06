package com.smilearts.smilenotes.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.navigation.NavigationView;
import com.smilearts.smilenotes.R;
import com.smilearts.smilenotes.adapter.NotesAdapter;
import com.smilearts.smilenotes.controller.RoomDB;
import com.smilearts.smilenotes.model.ColorUtil;
import com.smilearts.smilenotes.model.NotesModel;

import java.util.ArrayList;
import java.util.List;

public class Filter_Page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar myToolbar;
    RecyclerView data_list;
    ConstraintLayout no_Data;
    List<NotesModel> modelList;
    RoomDB roomDB;
    DrawerLayout drawerLayout ;
    NavigationView navigationView;
    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_page);
        initialise();
        getList();
    }

    private void initialise() {
        myToolbar = findViewById(R.id.filter_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationIcon(R.drawable.filter_icon_theme);

        data_list = findViewById(R.id.filter_list);
        no_Data = findViewById(R.id.filter_nodata);

        data_list.setHasFixedSize(true);
        data_list.setLayoutManager(new LinearLayoutManager(Filter_Page.this));

        roomDB = RoomDB.getInstance(Filter_Page.this);

        drawerLayout = findViewById(R.id.filter_layout);
        navigationView = findViewById(R.id.filter_nav_view);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                Filter_Page.this, drawerLayout, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        drawerLayout.openDrawer(GravityCompat.START);
        navigationView.setNavigationItemSelectedListener(this);
    }

    void getList(){
        modelList = roomDB.notesDao().getNotes();
        adapter = new NotesAdapter(modelList , Filter_Page.this);
        data_list.setAdapter(adapter);
        if(modelList.size()==0){
            no_Data.setVisibility(View.VISIBLE);
        }else {
            no_Data.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu , menu);
        MenuItem item = menu.findItem(R.id.filter_search_menu);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_priority:
                modelList = roomDB.notesDao().getNotesByPriority(1);
                NotesAdapter adapter = new NotesAdapter(modelList, Filter_Page.this);
                data_list.setAdapter(adapter);
                if(modelList.size()==0){
                    no_Data.setVisibility(View.VISIBLE);
                }else {
                    no_Data.setVisibility(View.GONE);
                }
                drawerLayout.closeDrawers();
                return true;
            case R.id.menu_non_priority:
                modelList = roomDB.notesDao().getNotesByPriority(0);
                NotesAdapter adapter1 = new NotesAdapter(modelList, Filter_Page.this);
                data_list.setAdapter(adapter1);
                if(modelList.size()==0){
                    no_Data.setVisibility(View.VISIBLE);
                }else {
                    no_Data.setVisibility(View.GONE);
                }
                drawerLayout.closeDrawers();
                return true;
            case R.id.menu_default:
                modelList = roomDB.notesDao().getNotesByColor(ColorUtil.DEFAULT);
                NotesAdapter adapter2 = new NotesAdapter(modelList, Filter_Page.this);
                data_list.setAdapter(adapter2);
                if(modelList.size()==0){
                    no_Data.setVisibility(View.VISIBLE);
                }else {
                    no_Data.setVisibility(View.GONE);
                }
                drawerLayout.closeDrawers();
                return true;
            case R.id.menu_lightblue:
                modelList = roomDB.notesDao().getNotesByColor(ColorUtil.LIGHTBLUE);
                NotesAdapter adapter3 = new NotesAdapter(modelList, Filter_Page.this);
                data_list.setAdapter(adapter3);
                if(modelList.size()==0){
                    no_Data.setVisibility(View.VISIBLE);
                }else {
                    no_Data.setVisibility(View.GONE);
                }
                drawerLayout.closeDrawers();
                return true;
            case R.id.menu_lightgreen:
                modelList = roomDB.notesDao().getNotesByColor(ColorUtil.LIGHTGREEN);
                NotesAdapter adapter4 = new NotesAdapter(modelList, Filter_Page.this);
                data_list.setAdapter(adapter4);
                if(modelList.size()==0){
                    no_Data.setVisibility(View.VISIBLE);
                }else {
                    no_Data.setVisibility(View.GONE);
                }
                drawerLayout.closeDrawers();
                return true;
            case R.id.menu_lighyellow:
                modelList = roomDB.notesDao().getNotesByColor(ColorUtil.LIGHTYELLOW);
                NotesAdapter adapter5 = new NotesAdapter(modelList, Filter_Page.this);
                data_list.setAdapter(adapter5);
                if(modelList.size()==0){
                    no_Data.setVisibility(View.VISIBLE);
                }else {
                    no_Data.setVisibility(View.GONE);
                }
                drawerLayout.closeDrawers();
                return true;
            case R.id.menu_darkyellow:
                modelList = roomDB.notesDao().getNotesByColor(ColorUtil.DARKYELLOW);
                NotesAdapter adapter6 = new NotesAdapter(modelList, Filter_Page.this);
                data_list.setAdapter(adapter6);
                if(modelList.size()==0){
                    no_Data.setVisibility(View.VISIBLE);
                }else {
                    no_Data.setVisibility(View.GONE);
                }
                drawerLayout.closeDrawers();
                return true;
            case R.id.menu_asc:
                modelList = roomDB.notesDao().getNotesASC();
                NotesAdapter adapter7 = new NotesAdapter(modelList, Filter_Page.this);
                data_list.setAdapter(adapter7);
                if(modelList.size()==0){
                    no_Data.setVisibility(View.VISIBLE);
                }else {
                    no_Data.setVisibility(View.GONE);
                }
                drawerLayout.closeDrawers();
                return true;
            case R.id.menu_desc:
                modelList = roomDB.notesDao().getNotesDESC();
                NotesAdapter adapter8 = new NotesAdapter(modelList, Filter_Page.this);
                data_list.setAdapter(adapter8);
                if(modelList.size()==0){
                    no_Data.setVisibility(View.VISIBLE);
                }else {
                    no_Data.setVisibility(View.GONE);
                }
                drawerLayout.closeDrawers();
                return true;
        }
        return false;
    }
}