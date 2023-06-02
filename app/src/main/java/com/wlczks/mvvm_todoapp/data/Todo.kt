package com.wlczks.mvvm_todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//encja
@Entity
data class Todo(

    @PrimaryKey
    val id: Int? = null,

    val title: String,

    val description: String?,

    val isDone: Boolean,

    val priority: String?,

    val date: String?,


    )
