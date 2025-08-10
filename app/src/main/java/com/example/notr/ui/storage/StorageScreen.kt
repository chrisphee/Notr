package com.example.notr.ui.storage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.AppTheme
import com.example.notr.data.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Main storage screen composable
 */
@Composable
fun StorageScreen(
    onNavigateToEdit: (Long) -> Unit = {},
    storageScreenViewModel: StorageScreenViewModel = viewModel(factory = StorageScreenViewModel.factory),
                  modifier:Modifier = Modifier) {
    EntryList(notesListFlow =  storageScreenViewModel.getAllNotes(),
        storageScreenViewModel = storageScreenViewModel,
        onNavigateToEdit = onNavigateToEdit)

}

/**
 * A card entry containing the notes
 */
@Composable
fun NoteEntryCard(note: Note = Note(-1, "TEST_ENTRY, TEST_ENTRY"),
                  storageScreenViewModel: StorageScreenViewModel,
                  onNavigateToEdit: (Long) -> Unit = {},
                  modifier: Modifier = Modifier){
    val scope = rememberCoroutineScope()

    Card(colors = CardDefaults.cardColors(
        //contentColor = Color.Gray
        ),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 25.dp)
            // User wants to edit the contents of the note
            .clickable {
                onNavigateToEdit(note.id)
            }

    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            // Date, ID, and Delete button go here
            Row(verticalAlignment = Alignment.CenterVertically) {

                /**
                Text(text = note.id.toString(),
                    style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.weight(1f))
                */

                Button(
                    onClick = {
                        scope.launch {
                            storageScreenViewModel.deleteNote(note)
                        }
                    }) {
                    Text(text = "DELETE")
                }
            }


            // The entry
            Text(text = note.entry,
                modifier = Modifier.padding(4.dp))
        }
    }
}

/**
 * The list which displays and holds the NoteEntryCard
 */
@Composable
fun EntryList(notesListFlow: Flow<List<Note>>,
              storageScreenViewModel: StorageScreenViewModel,
              onNavigateToEdit: (Long) -> Unit = {},
              modifier: Modifier = Modifier) {
    // Convert from Flow<List<>> to List<>
    val notesList by notesListFlow.collectAsState(initial = emptyList())

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(notesList) { note ->
            NoteEntryCard(
                note = note,
                storageScreenViewModel = storageScreenViewModel,
                onNavigateToEdit = onNavigateToEdit
            )
        }
    }
}
