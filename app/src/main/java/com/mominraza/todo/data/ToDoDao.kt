package com.mominraza.todo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToDo(toDo: ToDo)

    @Delete
    suspend fun deleteToDo(toDo: ToDo)

    @Query("select * from todo where id = :id")
    suspend fun getToDoById(id: Int): ToDo

    @Query("select * from todo")
    fun getToDos(): Flow<List<ToDo>>
}