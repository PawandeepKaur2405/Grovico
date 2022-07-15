package com.example.projectx.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.projectx.entities.Notes

@Dao
interface NotesDao {

    @Query("SELECT * FROM Notes")
    fun getNotes() : LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority = 3")
    fun getHighNotes() : LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority = 2")
    fun getMediumNotes() : LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority = 1")
    fun getLowNotes() : LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note : Notes)

    @Query("DELETE FROM Notes WHERE id =:id")
    fun deleteNote(id : Int)

    @Update
    fun updateNote(notes: Notes)

}