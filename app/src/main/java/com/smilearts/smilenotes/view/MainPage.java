package com.smilearts.smilenotes.view;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.smilearts.smilenotes.R;
import com.smilearts.smilenotes.adapter.NotesAdapter;
import com.smilearts.smilenotes.controller.RoomDB;
import com.smilearts.smilenotes.model.NotesModel;

import java.util.List;

public class MainPage extends AppCompatActivity {

    BottomAppBar myToolbar;
    RecyclerView list;
    List<NotesModel> modelList;
    RoomDB roomDB;
    SearchView searchView;
    ConstraintLayout noData;
    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        initialise();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        findViewById(R.id.main_add_notes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , AddNotes.class).putExtra("type","new"));
            }
        });

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

        noData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , AddNotes.class).putExtra("type","new"));
            }
        });

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , RecycleBin.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu , menu);
        menu.setHeaderTitle("Select Action");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.context_delete:
                roomDB.notesDao().DeleteNote(modelList.get(adapter.getPosition()).getId());
                adapter.notifyDataSetChanged();
                return true;
            case R.id.context_share:
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_setting:
                startActivity(new Intent(getApplicationContext() , SettingPage.class));
                break;
            case R.id.main_refresh:
                getList();
                break;
            case R.id.main_filter:
                startActivity(new Intent(getApplicationContext() , Filter_Page.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void getList(){
        modelList = roomDB.notesDao().getNotes();
        adapter = new NotesAdapter(modelList , MainPage.this);
        list.setAdapter(adapter);
        if(modelList.size()==0){
            noData.setVisibility(View.VISIBLE);
        }else {
            noData.setVisibility(View.GONE);
        }
    }

    private void initialise(){
        myToolbar = findViewById(R.id.main_appbar);
        setSupportActionBar(myToolbar);
        myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary) , PorterDuff.Mode.SRC_ATOP);
        list = findViewById(R.id.main_recycle);
        searchView = findViewById(R.id.main_search_view);
        roomDB = RoomDB.getInstance(MainPage.this);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(MainPage.this));
        noData = findViewById(R.id.main_nodata);
        registerForContextMenu(list);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ad = new AlertDialog.Builder(MainPage.this);
        ad.setTitle("Close");ad.setMessage("Are you sure want to close this app ?");
        ad.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finishAffinity();
            }
        });
        ad.setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ad.show();
    }
}