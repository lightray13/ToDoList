package com.todo.list.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.todo.list.data.database.dao.TaskDao
import com.todo.list.data.database.dao.TaskListDao
import com.todo.list.data.database.model.TaskEntity
import com.todo.list.data.database.model.TaskListEntity

@Database(entities = [TaskEntity::class, TaskListEntity::class], version = 1, exportSchema = false)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun taskListDao(): TaskListDao

    companion object {
        fun buildDatabase(context: Context): TaskDatabase {
            return Room.databaseBuilder(context, TaskDatabase::class.java, "Tasks").build()
        }
    }
}