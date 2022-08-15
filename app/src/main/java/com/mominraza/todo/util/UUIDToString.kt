package com.mominraza.todo.util

import androidx.room.TypeConverter
import java.util.UUID

class UUIDToString {
    @TypeConverter
    fun fromUUID(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter
    fun fromString(string: String): UUID {
        return UUID.fromString(string)
    }
}