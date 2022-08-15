package com.mominraza.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class ToDo(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String?,
    val isDone: Boolean,
)
