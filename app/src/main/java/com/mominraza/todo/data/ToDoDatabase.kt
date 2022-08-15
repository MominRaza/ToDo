package com.mominraza.todo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mominraza.todo.util.UUIDToString

@Database(entities = [ToDo::class], version = 1, exportSchema = false)
@TypeConverters(UUIDToString::class)
abstract class ToDoDatabase: RoomDatabase() {
    abstract val dao: ToDoDao
}