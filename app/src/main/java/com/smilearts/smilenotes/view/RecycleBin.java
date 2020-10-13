package com.smilearts.smilenotes.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.smilearts.smilenotes.R;
import com.smilearts.smilenotes.adapter.RecycleAdapter;
import com.smilearts.smilenotes.controller.RoomDB;
import com.smilearts.smilenotes.model.RecycleModel;

import java.util.List;

public class RecycleBin extends AppCompatActivity {

    Toolbar myToolbar;
    RecyclerView list;
    List<RecycleModel> adapterList;
    RecycleAdapter adapter;
    TextView count_txt;
    ConstraintLayout no_data;
    RoomDB roomDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_bin);
        initialise();
        getList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void getList(){
        adapterList = roomDB.notesDao().getRecycleList();
        adapter = new RecycleAdapter(adapterList , RecycleBin.this);
        list.setAdapter(adapter);
        if(adapterList.size()==0){
            no_data.setVisibility(View.VISIBLE);
            count_txt.setText("Total Count . 0");
        }else {
            no_data.setVisibility(View.GONE);
            count_txt.setText("Total Count . "+adapterList.size());
        }
    }

    private void initialise(){
        myToolbar = findViewById(R.id.recyclebin_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary) , PorterDuff.Mode.SRC_ATOP);
        list = findViewById(R.id.recyclebin_list);
        no_data = findViewById(R.id.recyclebin_nodata);
        count_txt = findViewById(R.id.recyclebin_count);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(RecycleBin.this));
        roomDB = RoomDB.getInstance(RecycleBin.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}