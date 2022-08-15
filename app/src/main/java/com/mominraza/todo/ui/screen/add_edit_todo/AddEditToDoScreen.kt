package com.mominraza.todo.ui.screen.add_edit_todo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mominraza.todo.util.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditToDoScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditToDoViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when(it) {
                UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        it.message,
                        it.action
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = { SmallTopAppBar(
            title = {Text("ToDo")},
            navigationIcon = {
                IconButton(onClick = { viewModel.onEvent(AddEditToDoEvent.OnBackButtonClick) }) {
                    Icon(Icons.Default.ArrowBack,null)
                }
            }
        ) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(8.dp)) {
            TextField(
                value = viewModel.title,
                onValueChange = {viewModel.onEvent(AddEditToDoEvent.OnTitleChange(it))},
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.description,
                onValueChange = {viewModel.onEvent(AddEditToDoEvent.OnDescriptionChange(it))},
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.onEvent(AddEditToDoEvent.OnSaveButtonClick) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}