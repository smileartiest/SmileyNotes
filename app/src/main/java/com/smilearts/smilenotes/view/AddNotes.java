package com.smilearts.smilenotes.view;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.smilearts.smilenotes.R;
import com.smilearts.smilenotes.controller.RoomDB;
import com.smilearts.smilenotes.controller.TimeDate;
import com.smilearts.smilenotes.model.ColorUtil;
import com.smilearts.smilenotes.model.NotesModel;
import com.smilearts.smilenotes.model.RecycleModel;

public class AddNotes extends AppCompatActivity {

    Toolbar myToolbar;
    TextView title, txt_title, txt_message, update, bg_default, bg_lightBlue, bg_lightGreen, bg_lightYellow, bg_darkYellow;
    ConstraintLayout update_panel;
    ImageView priority_icon , share_icon ;
    NotesModel model;
    RoomDB roomDB;
    TimeDate timeDate;
    String BG_COLOR;
    int priority = 0;
    boolean  sts = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_notes);
        initialise();
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (getIntent().getStringExtra("type").equals("update")) {
            model = roomDB.notesDao().getNote(getIntent().getIntExtra("id", 0));
            txt_title.setText(model.getTitle());
            txt_message.setText(model.getMessage());
            update.setText("Update");
            if(model.getPriority() == 0){
                priority_icon.setImageResource(R.drawable.non_priority_icon);
                sts = false;
                priority = 0;
            }else {
                sts = true;
                priority_icon.setImageResource(R.drawable.priority_icon);
                priority = 1;
            }
            if(model.getBg().equals(ColorUtil.DARKYELLOW)){
                txt_message.setBackgroundResource(R.color.DARKYELLOW);
                BG_COLOR = ColorUtil.DARKYELLOW;
            }else if(model.getBg().equals(ColorUtil.LIGHTBLUE)){
                txt_message.setBackgroundResource(R.color.LIGHTBLUE);
                BG_COLOR = ColorUtil.LIGHTBLUE;
            }else if(model.getBg().equals(ColorUtil.LIGHTGREEN)){
                txt_message.setBackgroundResource(R.color.LIGHTGREEN);
                BG_COLOR = ColorUtil.LIGHTGREEN;
            }else if(model.getBg().equals(ColorUtil.LIGHTYELLOW)){
                txt_message.setBackgroundResource(R.color.LIGHTYELLOW);
                BG_COLOR = ColorUtil.LIGHTYELLOW;
            }else {
                txt_message.setBackgroundResource(R.color.DEFAULT);
                BG_COLOR = ColorUtil.DEFAULT;
            }
            share_icon.setVisibility(View.VISIBLE);
            update.setVisibility(View.VISIBLE);
        } else {
            update.setText("Save");
            share_icon.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        findViewById(R.id.addnote_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getStringExtra("type").equals("update")) {
                    roomDB.notesDao().Update(getIntent().getIntExtra("id", 0), txt_title.getText().toString(), txt_message.getText().toString(), timeDate.getTime(), timeDate.getDateYMD() , BG_COLOR , priority);
                    Snackbar.make(v, "Update Successful", Snackbar.LENGTH_SHORT).show();
                    finish();
                } else {
                    model = new NotesModel();
                    model.setTitle(txt_title.getText().toString());
                    model.setMessage(txt_message.getText().toString());
                    model.setDate(timeDate.getDateYMD());
                    model.setTime(timeDate.getTime());
                    model.setBg(BG_COLOR);
                    model.setPriority(priority);
                    roomDB.notesDao().Insert(model);
                    Snackbar.make(v, "Save Successful", Snackbar.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        txt_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                txt_title.setFocusable(true);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    update.setVisibility(View.VISIBLE);
                } else {
                    update.setVisibility(View.GONE);
                }
            }
        });

        share_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Title : " + txt_title.getText().toString() + "\nNotes : \n" + txt_message.getText().toString());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bg_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BG_COLOR = ColorUtil.DEFAULT;
                txt_message.setBackgroundResource(R.color.DEFAULT);
            }
        });

        bg_darkYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BG_COLOR = ColorUtil.DARKYELLOW;
                txt_message.setBackgroundResource(R.color.DARKYELLOW);
            }
        });

        bg_lightYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BG_COLOR = ColorUtil.LIGHTYELLOW;
                txt_message.setBackgroundResource(R.color.LIGHTYELLOW);
            }
        });

        bg_lightGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BG_COLOR = ColorUtil.LIGHTGREEN;
                txt_message.setBackgroundResource(R.color.LIGHTGREEN);
            }
        });

        bg_lightBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BG_COLOR = ColorUtil.LIGHTBLUE;
                txt_message.setBackgroundResource(R.color.LIGHTBLUE);
            }
        });

        priority_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sts){
                    sts = false;
                    priority = 0;
                    priority_icon.setImageResource(R.drawable.non_priority_icon);
                }else {
                    sts = true;
                    priority = 1;
                    priority_icon.setImageResource(R.drawable.priority_icon);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getIntent().getStringExtra("type").equals("update")) {
            getMenuInflater().inflate(R.menu.delete_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                roomDB.notesDao().DeleteNote(getIntent().getIntExtra("id", 0));
                Toast.makeText(this, "Delete successful", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.menu_move_bin:
                RecycleModel model1 = new RecycleModel();
                model1.setTitle(model.getTitle());
                model1.setMessage(model.getMessage());
                model1.setDate(new TimeDate().getDateYMD());
                roomDB.notesDao().InsertRecycle(model1);
                roomDB.notesDao().DeleteNote(model.getId());
                Toast.makeText(this, "Move to Recycle Bin", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openDialog() {
        Toast.makeText(this, "Please Enable Permission", Toast.LENGTH_SHORT).show();
    }

    private void initialise() {
        myToolbar = findViewById(R.id.addnote_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        myToolbar.getOverflowIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        title = findViewById(R.id.addnote_title);
        txt_title = findViewById(R.id.addnote_txt_title);
        txt_message = findViewById(R.id.addnote_txt_message);
        update = findViewById(R.id.addnote_update);
        update_panel = findViewById(R.id.addnote_update_panel);
        bg_default = findViewById(R.id.addnote_default_bg);
        bg_lightBlue = findViewById(R.id.addnote_lightblue_bg);
        bg_lightGreen = findViewById(R.id.addnote_lightgreen_bg);
        bg_lightYellow = findViewById(R.id.addnote_lightyellow_bg);
        bg_darkYellow = findViewById(R.id.addnote_darkyellow_bg);
        priority_icon = findViewById(R.id.addnote_setpriority);
        share_icon = findViewById(R.id.addnote_txt_share);
        roomDB = RoomDB.getInstance(AddNotes.this);
        timeDate = new TimeDate();
        BG_COLOR = ColorUtil.DEFAULT;
        update.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}