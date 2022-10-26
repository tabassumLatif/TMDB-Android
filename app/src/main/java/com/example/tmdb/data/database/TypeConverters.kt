package com.example.tmdb.data.database

import androidx.room.TypeConverter

class TypeConverters {

    @TypeConverter
    fun fromBooleanToInt(bool: Boolean): Int {
        return if (bool) {
            1
        } else {
            0
        }
    }

    @TypeConverter
    fun fromIntToBool(ina: Int): Boolean {
        return ina == 1
    }
}