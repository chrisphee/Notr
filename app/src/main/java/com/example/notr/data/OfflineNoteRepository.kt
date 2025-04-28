package com.example.notr.data

import kotlinx.coroutines.flow.Flow

class OfflineNoteRepository(private val noteDao: NoteDao) : NoteRepository{
    override fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    override suspend fun insertNote(note: Note) = noteDao.insertNote(note)

    override suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
}