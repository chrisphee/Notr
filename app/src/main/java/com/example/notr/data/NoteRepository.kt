package com.example.notr.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository that does CRUD operations of [Note] entity class
 */

interface NoteRepository {
    /**
     * Retrieve all notes
     */
    fun getAllNotes(): Flow<List<Note>>

    /**
     * Insert an item into the database
     */
    suspend fun insertNote(note: Note)

    /**
     * Deletes a note
     */
    suspend fun deleteNote(note: Note)
}