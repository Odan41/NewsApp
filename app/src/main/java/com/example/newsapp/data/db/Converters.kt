package com.example.umte_project_news.data.db

import androidx.room.TypeConverter
import com.example.umte_project_news.data.models.Source

class Converters {
    @TypeConverter
    fun fromSource(source: Source):String {
        return source.name
    }

    @TypeConverter
    fun toSource(name:String): Source {
        return Source(name,name)
    }
}