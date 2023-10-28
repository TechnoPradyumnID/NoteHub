package com.technopradyumn.notehub.Dao

import com.technopradyumn.notehub.Models.Note
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note : Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notesTable ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Update
    suspend fun update(note: Note)

}
