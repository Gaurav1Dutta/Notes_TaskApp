package com.example.notes_taskapp

interface ClickInterface {
    fun  onDeleteClick(notesEntities: NotesEntites)

    fun onEditClick(notesEntities: NotesEntites)
}

interface TaskClickInterface {
    fun onCompleteClick(taskEntites: TaskEntites)
    fun onEditTask(taskEntites: TaskEntites)
}