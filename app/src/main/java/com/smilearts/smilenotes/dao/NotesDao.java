package com.smilearts.smilenotes.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.smilearts.smilenotes.model.NotesModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NotesDao {

    @Insert
    void Insert(NotesModel model);

    @Query("SELECT * FROM Notes ORDER BY Priority DESC")
    List<NotesModel> getNotes();

    @Query("SELECT * FROM Notes ORDER BY Title DESC")
    List<NotesModel> getNotesDESC();

    @Query("SELECT * FROM Notes ORDER BY Title ASC")
    List<NotesModel> getNotesASC();

    @Query("SELECT * FROM Notes WHERE Id =:Id")
    NotesModel getNote(int Id);

    @Query("SELECT * FROM Notes WHERE Title =:title")
    List<NotesModel> getNotesByTitle(String title);

    @Query("SELECT * FROM Notes WHERE Priority =:pri")
    List<NotesModel> getNotesByPriority(int pri);

    @Query("SELECT * FROM Notes WHERE Bg =:bg")
    List<NotesModel> getNotesByColor(String bg);

    @Query("UPDATE Notes SET Title =:title , Message =:message , Time =:time , Date =:date , Bg =:bg , Priority =:pri WHERE Id =:id")
    void Update(int id , String title , String message , String time , String date , String bg , int pri);

    @Query("DELETE FROM Notes WHERE Id=:Id")
    void DeleteNote(int Id);

    @Query("DELETE FROM Notes")
    void ClearNotes();

}
