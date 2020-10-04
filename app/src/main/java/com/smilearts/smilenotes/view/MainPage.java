package com.smilearts.smilenotes.view;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.PopupMenu;

import com.smilearts.smilenotes.R;
import com.smilearts.smilenotes.adapter.NotesAdapter;
import com.smilearts.smilenotes.controller.RoomDB;
import com.smilearts.smilenotes.model.ColorUtil;
import com.smilearts.smilenotes.model.NotesModel;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity {

    Toolbar myToolbar;
    RecyclerView list;
    List<NotesModel> modelList;
    RoomDB roomDB;
    AutoCompleteTextView search_bar;
    ArrayList<String> title_list ;
    ConstraintLayout noData;

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

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , AddNotes.class).putExtra("type","new"));
            }
        });

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                modelList = roomDB.notesDao().getNotesByTitle(editable.toString());
                NotesAdapter adapter = new NotesAdapter(modelList , MainPage.this);
                list.setAdapter(adapter);
            }
        });

        findViewById(R.id.main_filter).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(final View view) {
                final PopupMenu menu = new PopupMenu(MainPage.this , view);
                menu.inflate(R.menu.filter_menu);
                menu.setGravity(Gravity.END);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.filter_priority:
                                PopupMenu menu1 = new PopupMenu(MainPage.this , view);
                                menu1.inflate(R.menu.priority_menu);
                                menu1.setGravity(Gravity.END);
                                menu1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem menuItem) {
                                        switch (menuItem.getItemId()){
                                            case R.id.menu_priority:
                                                modelList = roomDB.notesDao().getNotesByPriority(1);
                                                NotesAdapter adapter = new NotesAdapter(modelList , MainPage.this);
                                                list.setAdapter(adapter);
                                                return true;
                                            case R.id.menu_non_priority:
                                                modelList = roomDB.notesDao().getNotesByPriority(0);
                                                NotesAdapter adapter1 = new NotesAdapter(modelList , MainPage.this);
                                                list.setAdapter(adapter1);
                                                return true;
                                        }
                                        return false;
                                    }
                                });
                                menu1.show();
                                return true;
                            case R.id.filter_color:
                                PopupMenu menu2 = new PopupMenu(MainPage.this , view);
                                menu2.inflate(R.menu.color_menu);
                                menu2.setGravity(Gravity.END);
                                menu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem menuItem) {
                                        switch (menuItem.getItemId()){
                                            case R.id.menu_default:
                                                modelList = roomDB.notesDao().getNotesByColor(ColorUtil.DEFAULT);
                                                NotesAdapter adapter = new NotesAdapter(modelList , MainPage.this);
                                                list.setAdapter(adapter);
                                                return true;
                                            case R.id.menu_lightblue:
                                                modelList = roomDB.notesDao().getNotesByColor(ColorUtil.LIGHTBLUE);
                                                NotesAdapter adapter2 = new NotesAdapter(modelList , MainPage.this);
                                                list.setAdapter(adapter2);
                                                return true;
                                            case R.id.menu_lightgreen:
                                                modelList = roomDB.notesDao().getNotesByColor(ColorUtil.LIGHTGREEN);
                                                NotesAdapter adapter3 = new NotesAdapter(modelList , MainPage.this);
                                                list.setAdapter(adapter3);
                                                return true;
                                            case R.id.menu_lighyellow:
                                                modelList = roomDB.notesDao().getNotesByColor(ColorUtil.LIGHTYELLOW);
                                                NotesAdapter adapter4 = new NotesAdapter(modelList , MainPage.this);
                                                list.setAdapter(adapter4);
                                                return true;
                                            case R.id.menu_darkyellow:
                                                modelList = roomDB.notesDao().getNotesByColor(ColorUtil.DARKYELLOW);
                                                NotesAdapter adapter5 = new NotesAdapter(modelList , MainPage.this);
                                                list.setAdapter(adapter5);
                                                return true;
                                        }
                                        return false;
                                    }
                                });
                                menu2.show();
                                menu2.setOnDismissListener(new PopupMenu.OnDismissListener() {
                                    @Override
                                    public void onDismiss(PopupMenu popupMenu) {
                                        popupMenu.dismiss();
                                    }
                                });
                                return true;
                            case R.id.filter_shorting:
                                PopupMenu menu3 = new PopupMenu(MainPage.this , view);
                                menu3.inflate(R.menu.short_menu);
                                menu3.setGravity(Gravity.END);menu3.show();
                                menu3.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem menuItem) {
                                        switch (menuItem.getItemId()){
                                            case R.id.menu_asc:
                                                modelList = roomDB.notesDao().getNotesASC();
                                                NotesAdapter adapter = new NotesAdapter(modelList , MainPage.this);
                                                list.setAdapter(adapter);
                                                return true;
                                            case R.id.menu_desc:
                                                modelList = roomDB.notesDao().getNotesDESC();
                                                NotesAdapter adapter2 = new NotesAdapter(modelList , MainPage.this);
                                                list.setAdapter(adapter2);
                                                return true;
                                        }
                                        return false;
                                    }
                                });
                                menu3.setOnDismissListener(new PopupMenu.OnDismissListener() {
                                    @Override
                                    public void onDismiss(PopupMenu popupMenu) {
                                        popupMenu.dismiss();
                                    }
                                });
                                return true;
                        }
                        return false;
                    }
                });
                menu.show();
            }
        });

        noData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , AddNotes.class).putExtra("type","new"));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_setting:
                startActivity(new Intent(getApplicationContext() , SettingPage.class));
                break;
            case R.id.main_refresh:
                search_bar.setText("");
                getList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void getList(){
        modelList = roomDB.notesDao().getNotes();
        NotesAdapter adapter = new NotesAdapter(modelList , MainPage.this);
        list.setAdapter(adapter);
        if(modelList.size()==0){
            noData.setVisibility(View.VISIBLE);
        }else {
            noData.setVisibility(View.GONE);
        }
        title_list = new ArrayList<>();
        for(int i = 0 ; i < modelList.size() ; i++){
            title_list.add(modelList.get(i).getTitle());
        }
        ArrayAdapter<String> ad = new ArrayAdapter(MainPage.this , R.layout.spin_list , title_list);
        search_bar.setAdapter(ad);
        search_bar.setThreshold(1);
    }

    private void initialise(){
        myToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationIcon(R.drawable.add_icon);
        list = findViewById(R.id.main_recycle);
        search_bar = findViewById(R.id.main_searchbar);
        roomDB = RoomDB.getInstance(MainPage.this);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(MainPage.this));
        noData = findViewById(R.id.main_nodata);
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