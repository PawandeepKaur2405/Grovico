package com.example.projectx.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.projectx.daos.NotesDao
import com.example.projectx.database.NotesDb
import com.example.projectx.entities.Notes
import com.example.projectx.repository.NotesRepository

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    val repository: NotesRepository

    init {
        val dao = NotesDb.getDatabaseInstance(application).myNotesDao()
        repository = NotesRepository(dao)
    }

    fun addNotes(notes: Notes){
        repository.insertNotes(notes)
    }

    fun deleteNote(id : Int){
        repository.deleteNote(id)
    }

    fun getNotes() : LiveData<List<Notes>>{
        return repository.getAllNotes()
    }

    fun getHighNotes() : LiveData<List<Notes>>{
        return repository.getHighNotes()
    }

    fun getMediumNotes() : LiveData<List<Notes>>{
        return repository.getMediumNotes()
    }
    fun getLowNotes() : LiveData<List<Notes>>{
        return repository.getLowNotes()
    }

    fun updateNote(notes: Notes){
        repository.updateNote(notes)
    }
}