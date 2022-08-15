package com.mominraza.todo.ui.add_edit_todo

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.mominraza.todo.util.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditToDoScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditToDoViewModel = hiltViewModel()
) {
    val snackbarHostState = SnackbarHostState()

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
    ) {
        Column() {
            TextField(
                value = viewModel.title,
                onValueChange = {viewModel.onEvent(AddEditToDoEvent.OnTitleChange(it))},
                label = { Text("Title") }
            )
            TextField(
                value = viewModel.description,
                onValueChange = {viewModel.onEvent(AddEditToDoEvent.OnDescriptionChange(it))},
                label = { Text("Description") }
            )
            Button(onClick = { viewModel.onEvent(AddEditToDoEvent.OnSaveButtonClick) }) {
                Text("Save")
            }
        }
    }
}