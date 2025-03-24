package com.example.notr.ui.home

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenViewModel(private val noteDao: NoteDao): ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    var currentUserEntry: String by mutableStateOf("")
        private set

    /*
     *   Updates the current user entry
     */
    fun updateCurrentUserEntry(updatedEntry: String) {
        currentUserEntry = updatedEntry
    }

    /*
     * User pressed save, so we add an entry
     */
    suspend fun addNote(entry: String) {
        noteDao.insertNote(Note(entry = currentUserEntry))
        updateCurrentUserEntry("")
    }

    /*
     * Factory for initialisation
     */
    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NotrApplication)
                HomeScreenViewModel(application.database.noteDao())
            }
        }
    }


}

data class HomeScreenUiState(
    var dummy: String = ""
)