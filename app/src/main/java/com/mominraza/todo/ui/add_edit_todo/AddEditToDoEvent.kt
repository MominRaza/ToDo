package com.mominraza.todo.ui.add_edit_todo

sealed class AddEditToDoEvent() {
    data class OnTitleChange(val title: String): AddEditToDoEvent()
    data class OnDescriptionChange(val description: String): AddEditToDoEvent()
    object OnSaveButtonClick: AddEditToDoEvent()
    object OnBackButtonClick: AddEditToDoEvent()
}
