package com.mominraza.todo.data

import kotlinx.coroutines.flow.Flow

class ToDoRepositoryImpl(
    private val dao: ToDoDao
): ToDoRepository {
    override suspend fun addToDo(toDo: ToDo) {
        dao.addToDo(toDo)
    }

    override suspend fun deleteToDo(toDo: ToDo) {
        dao.deleteToDo(toDo)
    }

    override suspend fun getToDoById(id: String): ToDo {
        return dao.getToDoById(id)
    }

    override fun getToDos(): Flow<List<ToDo>> {
        return dao.getToDos()
    }
}