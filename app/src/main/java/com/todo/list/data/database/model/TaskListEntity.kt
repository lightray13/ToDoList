package com.todo.list.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list")
data class TaskListEntity(
    @PrimaryKey val listName: String
)