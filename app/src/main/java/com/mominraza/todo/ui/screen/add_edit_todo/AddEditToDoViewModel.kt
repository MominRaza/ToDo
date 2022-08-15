package com.mominraza.todo.ui.screen.add_edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mominraza.todo.data.ToDo
import com.mominraza.todo.data.ToDoRepository
import com.mominraza.todo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditToDoViewModel @Inject constructor(
    private val repository: ToDoRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var toDo by mutableStateOf<ToDo?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val toDoId = savedStateHandle.get<String>("todoId")
        if (!toDoId.isNullOrBlank()) {
            viewModelScope.launch {
                repository.getToDoById(toDoId).let {
                    title = it.title
                    description = it.description ?: ""
                    toDo = it
                }
            }
        }
    }

    fun onEvent(event: AddEditToDoEvent) {
        when(event) {
            is AddEditToDoEvent.OnTitleChange -> title = event.title
            is AddEditToDoEvent.OnDescriptionChange -> description = event.description
            AddEditToDoEvent.OnSaveButtonClick -> {
                viewModelScope.launch {
                    if (title.isBlank()) {
                        sendUiEvent(
                            UiEvent.ShowSnackbar("The title can't be empty")
                        )
                        return@launch
                    }
                    repository.addToDo(
                        (toDo?.copy(
                            title = title,
                            description = description,
                        ) ?: ToDo(
                            title = title,
                            description = description,
                        ))
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
            AddEditToDoEvent.OnBackButtonClick -> sendUiEvent(UiEvent.PopBackStack)
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}