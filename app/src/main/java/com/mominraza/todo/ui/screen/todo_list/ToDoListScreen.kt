package com.mominraza.todo.ui.screen.todo_list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mominraza.todo.util.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ToDoListViewModel = hiltViewModel()
) {
    val todos by viewModel.todos.collectAsState(initial = emptyList())
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect{
            when(it){
                is UiEvent.Navigate -> onNavigate(it)
                is UiEvent.ShowSnackbar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = it.message,
                        actionLabel = it.action
                    )
                    if (result == SnackbarResult.ActionPerformed)
                        viewModel.onEvent(ToDoListEvent.OnUndoDeleteClick)
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = { SmallTopAppBar(title = {Text("ToDo")}) },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onEvent(ToDoListEvent.OnAddToDoClick) }) {
                Icon(Icons.Default.Add,"Add")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(4.dp,4.dp,4.dp,86.dp)
        ) {
            items(todos, { it.id }) {
                ToDoItem(
                    toDo = it,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}