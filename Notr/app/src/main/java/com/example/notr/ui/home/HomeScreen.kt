package com.example.notr.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.AppTheme
import com.example.compose.surfaceVariantDark
import kotlinx.coroutines.launch

/*
 * Main screen composable entry point call
 */

@Composable
fun HomeScreen(homeScreenViewmodel: HomeScreenViewModel = viewModel(factory = HomeScreenViewModel.factory),
                modifier: Modifier = Modifier) {

    Column (modifier = modifier.fillMaxSize()) {
        TextBoxComposable(
            onValueChanged = { homeScreenViewmodel.updateCurrentUserEntry(it) },
            changedEntry = homeScreenViewmodel.currentUserEntry,
            modifier = modifier
        )

        ControlBarComposable(
            homeScreenViewmodel,
            {})

    }

}


/*
 * Textbox where the user will enter their notes
 */
@Composable
fun TextBoxComposable(userEntry: String = "",
                      onValueChanged: (String) -> Unit,
                      changedEntry: String,
                      modifier: Modifier = Modifier) {
    TextField(
        value = changedEntry,
        onValueChange = onValueChanged,
        placeholder = {
            // Transparent Place holder
            Text(
                text = "Enter text here...",
                color = Color.Gray.copy(alpha = 0.5f)
            )
        },

        textStyle = MaterialTheme.typography.bodyLarge,

        modifier = Modifier
            .fillMaxHeight(0.8f)
            .fillMaxWidth()
            //.padding(16.dp)
    )
}

/*
 * Place where the buttons are (at the bottom of the screen)
 */

@Composable
fun ControlBarComposable(homeScreenViewmodel: HomeScreenViewModel,
                         onDeleteButtonClicked: () -> Unit,
                         modifier: Modifier = Modifier) {

    val scope = rememberCoroutineScope()

    Surface (
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Save Button here
            Button(onClick = {
                    scope.launch{
                    homeScreenViewmodel.addNote(homeScreenViewmodel.currentUserEntry)
                    }
                             },
                   modifier = modifier
                       .width(164.dp)
                       .height(96.dp)) {
                Text(text = "SAVE",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier)
            }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Save as and Delete button here
            Column () {
                Button(onClick = onDeleteButtonClicked,
                       modifier = modifier
                           .width(164.dp)
                           .height(96.dp)) {
                    Text(text = "DELETE",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier)
                }
            }
        }
}


@Preview
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen()
    }
}
