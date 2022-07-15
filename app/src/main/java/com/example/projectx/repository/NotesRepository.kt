package com.example.projectx.repository

import androidx.lifecycle.LiveData
import com.example.projectx.daos.NotesDao
import com.example.projectx.entities.Notes

class NotesRepository(val dao: NotesDao) {
    fun getAllNotes() : LiveData<List<Notes>>{
         return dao.getNotes()
    }

    fun insertNotes(notes: Notes){
        dao.insertNote(notes)
    }

    fun getHighNotes() : LiveData<List<Notes>> = dao.getHighNotes()

    fun getMediumNotes() : LiveData<List<Notes>> = dao.getMediumNotes()

    fun getLowNotes() : LiveData<List<Notes>> = dao.getLowNotes()

    fun deleteNote(id : Int){
        dao.deleteNote(id)
    }

    fun updateNote(notes: Notes){
        dao.updateNote(notes)
    }
}