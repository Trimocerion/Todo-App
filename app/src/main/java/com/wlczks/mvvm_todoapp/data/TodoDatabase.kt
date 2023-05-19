package com.wlczks.mvvm_todoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

//baza danych
@Database(
    entities = [Todo::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase: RoomDatabase() {

    abstract val dao: TodoDao
}