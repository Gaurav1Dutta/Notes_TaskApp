package com.example.notes_taskapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDbInterface {
    @Insert
    fun InsertNotes(notes: NotesEntites) : Long//variable : entity , now connect dao with database

    @Query("Select* From NotesEntites")
    fun getNotes():List<NotesEntites>

    @Delete
    fun DeleteNotes(notes_entities : NotesEntites) //to delete

    @Update
    fun UpdateNotes(notesEntities: NotesEntites)//to edit / update

    @Query("SELECT * FROM  NotesEntites WHERE id = :id")
    fun getNotesById(id: Int) : NotesEntites
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Insert
    fun InsertTasks(tasks: TaskEntites) : Long

    @Update
    fun UpdateTasks(tasks: TaskEntites)

    @Query("SELECT * FROM  TaskEntites WHERE id = :id")
    fun getTasksById(id: Int) : TaskEntites

    @Query("Select * From TaskEntites")
    fun getTasks():List<TaskEntites>
    @Delete
    fun DeleteTasks(tasks: TaskEntites)
}