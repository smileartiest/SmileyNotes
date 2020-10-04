package com.smilearts.smilenotes.controller;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.smilearts.smilenotes.dao.NotesDao;
import com.smilearts.smilenotes.model.NotesModel;

@Database(entities = {NotesModel.class} , version = 2, exportSchema = true)
public abstract class RoomDB extends RoomDatabase {

    public static String DB_NAME = "NOTES_DB";
    public abstract NotesDao notesDao();
    public static RoomDB INSTANCE;

    public static synchronized RoomDB getInstance(Context mContext){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(mContext , RoomDB.class , DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

}
