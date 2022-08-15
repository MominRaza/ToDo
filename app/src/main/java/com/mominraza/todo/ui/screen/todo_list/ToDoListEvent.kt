package com.mominraza.todo.ui.screen.todo_list

import com.mominraza.todo.data.ToDo

sealed class ToDoListEvent {
    data class OnDeleteToDoClick(val toDo: ToDo): ToDoListEvent()
    data class OnDoneChange(val toDo: ToDo, val isDone: Boolean): ToDoListEvent()
    object OnUndoDeleteClick: ToDoListEvent()
    data class OnToDoClick(val toDo: ToDo): ToDoListEvent()
    object OnAddToDoClick: ToDoListEvent()
}
