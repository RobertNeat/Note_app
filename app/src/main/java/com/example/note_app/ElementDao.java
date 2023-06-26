package com.example.note_app;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ElementDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)//IGNORE <- wtedy będzie pozwalało wielokrotnie dodawać to samo
    void insert(Element note);

    @Update
    void update(Element note);

    @Delete
    void delete(Element note);

    @Query("DELETE FROM note_table")
    void deleteAll();

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    LiveData<List<Element>> getOrderedPhones();//ordered by adding order

    //usunięcie elementu na podstawie klucza id
    @Query("DELETE FROM note_table WHERE id = :id")
    void deleteById(int id);

}
