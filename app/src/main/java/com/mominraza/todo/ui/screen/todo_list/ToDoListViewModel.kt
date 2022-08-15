package com.mominraza.todo.ui.screen.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mominraza.todo.data.ToDo
import com.mominraza.todo.data.ToDoRepository
import com.mominraza.todo.util.Routes
import com.mominraza.todo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(
    private val repository: ToDoRepository
): ViewModel() {
    val todos = repository.getToDos()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedToDo: ToDo? = null

    fun onEvent(event: ToDoListEvent) {
        when(event) {
            ToDoListEvent.OnAddToDoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is ToDoListEvent.OnDeleteToDoClick -> {
                deletedToDo = event.toDo
                viewModelScope.launch {
                    repository.deleteToDo(event.toDo)
                }
                sendUiEvent(UiEvent.ShowSnackbar("ToDo deleted","Undo"))
            }
            is ToDoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repository.addToDo(
                        event.toDo.copy(isDone = event.isDone)
                    )
                }
            }
            is ToDoListEvent.OnToDoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.toDo.id}"))
            }
            ToDoListEvent.OnUndoDeleteClick -> {
                deletedToDo?.let {
                    viewModelScope.launch {
                        repository.addToDo(it)
                    }
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}