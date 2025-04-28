package com.example.notr.ui.storage

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.notr.NotrApplication
import com.example.notr.data.Note
import com.example.notr.data.NoteRepository
import com.example.notr.ui.home.HomeScreenViewModel
import kotlinx.coroutines.flow.Flow

class StorageScreenViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    /**
     * Retrieves all notes
     */
    fun getAllNotes(): Flow<List<Note>>{
        return noteRepository.getAllNotes()
    }



    /**
     * Factory for initialisation
     */
    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NotrApplication)
                StorageScreenViewModel(application.container.noteRepository)
            }
        }
    }
}