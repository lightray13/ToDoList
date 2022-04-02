package com.todo.list.data.repository.newTask

import androidx.lifecycle.LiveData
import com.todo.list.data.database.TaskDatabase
import com.todo.list.data.database.model.TaskEntity
import com.todo.list.data.database.model.TaskListEntity
import javax.inject.Inject

class NewTaskDataSource @Inject constructor(private val database: TaskDatabase) {
    val allTasksLists: LiveData<List<TaskListEntity>> = database.taskListDao().taskList()

    suspend fun insertTaskIntoDatabase(taskToInsert: TaskEntity): Boolean {
        if (taskToInsert.name.isNullOrEmpty().not()) {
            if (database.taskDao().insertTaskEntity(taskToInsert) > 0) {
                return true
            }
            return false
        }
        return false
    }

    suspend fun insertTasksListIntoDatabase(taskListToInsert: TaskListEntity): Boolean {
            if (database.taskListDao().insertListTaskEntity(taskListToInsert) > 0) {
                return true
            }
            return false
    }
}