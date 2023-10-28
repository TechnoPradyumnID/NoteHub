package com.technopradyumn.notehub.Database

import androidx.lifecycle.LiveData
import com.technopradyumn.notehub.Dao.NotesDao
import com.technopradyumn.notehub.Models.Note

class NoteRepository(private val notesDao: NotesDao) {

    val allNotes: LiveData<List<Note>> = notesDao.getAllNotes()

    suspend fun insert(note: Note) {
        notesDao.insert(note)
    }

    suspend fun delete(note: Note){
        notesDao.delete(note)
    }

    suspend fun update(note: Note){
        notesDao.update(note)
    }
}
