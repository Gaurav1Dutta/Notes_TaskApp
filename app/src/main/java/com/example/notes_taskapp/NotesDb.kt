package com.example.notes_taskapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NotesEntites::class, TaskEntites::class], version = 1)
    abstract class      NotesDb : RoomDatabase() {
        abstract  fun notesDbInterface() : NotesDbInterface

        companion object{
            var notesDb : NotesDb?=null
            @Synchronized
            fun getDataBase(context: Context): NotesDb {
                if (notesDb ==null)
                {
                    notesDb = Room.databaseBuilder(
                        context, NotesDb::class.java,context.resources.getString(R.string.app_name)).build()
                }
                return notesDb !!
            }

        }
    }