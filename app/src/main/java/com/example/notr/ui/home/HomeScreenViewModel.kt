package com.example.notr.ui.home

import android.util.Log
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.notr.NotrApplication
import com.example.notr.data.Note
import com.example.notr.data.NoteDao
import com.example.notr.data.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenViewModel(private val noteRepository: NoteRepository): ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    // Persistent Variables
    var currentUserEntry: String by mutableStateOf("")
        private set
    var currentNoteId: Long? by mutableStateOf(null)

    var isEditMode: Boolean by mutableStateOf(false)



    /**
     *   Updates the current user entry
     */
    fun updateCurrentUserEntry(updatedEntry: String) {
        currentUserEntry = updatedEntry
    }

    /**
     * Saves user entry to the database
     */
    suspend fun addNote(entry: String) {

        if (currentUserEntry != ""){
            // We are editing an existing note, so UPDATE not INSERT
            if (isEditMode && currentNoteId != null) {
                noteRepository.updateNote(Note(id = currentNoteId!!, entry = currentUserEntry))
                showNotification("Note Updated")
            // New note so INSERT instead
            } else {
                noteRepository.insertNote(Note(entry = currentUserEntry))
                updateCurrentUserEntry("")
                // TODO: DISPLAY "SAVED" NOTIFICATION
                showNotification("Note Saved")
            }
        }
        else {
            // TODO: DISPLAY "CANNOT SAVE EMPTY" NOTIFICATION
            Log.d("D", "EMPTY ENTRY ATTEMPTED")
            showNotification("Cannot Save Empty Note")
        }
    }

    /**
     * Resets edit mode states
     */
    fun resetEditMode() {
        currentUserEntry = ""
        currentNoteId = null
        isEditMode = false
    }

    /**
     * Loads a note based on ID to the main editing text box to the persistent variables
     */
    suspend fun loadNote(noteId: Long) {
        val note: Note? = noteRepository.getCustomNote(noteId)
        if (note != null) {
            currentNoteId = noteId
            updateCurrentUserEntry(note.entry)
            isEditMode = true
        } else {
            Log.d("D", "CANNOT RETRIEVE NOTE")
        }
    }


    /**
     * Shows a notification to the user
     */
    private fun showNotification(message: String, isError: Boolean = false) {
        _uiState.value = HomeScreenUiState(
            showNotification = true,
            notificationMessage = message,
            isError = isError
        )
    }

    /**
     * Clears the current notification
     */
    fun clearNotification() {
        _uiState.value = HomeScreenUiState(
            showNotification = false,
            isError = false
        )
    }

    /**
     * Clears the current entry
     */
    fun clearEntry() {
        updateCurrentUserEntry("")
    }


    /**
     * Factory for initialisation
     */
    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NotrApplication)
                HomeScreenViewModel(application.container.noteRepository)
            }
        }
    }


}

data class HomeScreenUiState(
    val showNotification: Boolean = false,
    val notificationMessage: String = "",
    val isError: Boolean = false
)