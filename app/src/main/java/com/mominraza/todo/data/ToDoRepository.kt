package com.mominraza.todo.data

import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    suspend fun addToDo(toDo: ToDo)

    suspend fun deleteToDo(toDo: ToDo)

    suspend fun getToDoById(id: Int): ToDo

    fun getToDos(): Flow<List<ToDo>>
}