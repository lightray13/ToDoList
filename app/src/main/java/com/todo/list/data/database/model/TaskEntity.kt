package com.todo.list.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_list")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var name: String?,
    var date: String?,
    var time: String?,
    var listName: String?,
    var listPosition: Int?,
    var priority: Int?,
    var isCompleted: Boolean = false
)
