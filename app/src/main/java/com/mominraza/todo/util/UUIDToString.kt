package com.mominraza.todo.util

import androidx.room.TypeConverter
import java.util.UUID

class UUIDToString {
    @TypeConverter
    fun StringfromUUID(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter
    fun UUIDfromString(string: String): UUID {
        return UUID.fromString(string)
    }
}