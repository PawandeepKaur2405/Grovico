package com.example.projectx.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projectx.daos.NotesDao
import com.example.projectx.entities.Notes

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDb : RoomDatabase() {
    abstract fun myNotesDao() : NotesDao

    companion object{
        @Volatile
        var INSTANCE : NotesDb?= null

        fun getDatabaseInstance(context: Context) : NotesDb {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val roomDatabaseInstace = Room.databaseBuilder(context, NotesDb::class.java, "Notes").allowMainThreadQueries().build()
                INSTANCE =roomDatabaseInstace
                return roomDatabaseInstace
            }
        }

    }
}