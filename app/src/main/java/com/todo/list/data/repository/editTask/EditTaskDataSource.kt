package com.todo.list.data.repository.editTask

import androidx.lifecycle.LiveData
import com.todo.list.data.database.TaskDatabase
import com.todo.list.data.database.model.TaskEntity
import com.todo.list.data.database.model.TaskListEntity
import javax.inject.Inject

class EditTaskDataSource @Inject constructor(private val database: TaskDatabase) {
    fun taskById(id: Long) = database.taskDao().taskLiveDataFromId(id)
    val allTasksLists: LiveData<List<TaskListEntity>> = database.taskListDao().taskList()

    suspend fun insertTasksListIntoDatabase(taskListToInsert: TaskListEntity): Boolean {
        if (database.taskListDao().insertListTaskEntity(taskListToInsert) > 0) {
            return true
        }
        return false
    }

    suspend fun deleteTask(id: Long): Boolean {
        val task = database.taskDao().taskFromId(id)
        task?.let {
            if (database.taskDao().deleteTaskListEntity(task) > 0) {
                return true
            }
        }
        return false
    }

    suspend fun updateTask(taskEntity: TaskEntity): Boolean {
        if (database.taskDao().updateTaskListEntity(taskEntity) > 0) {
            return true
        }
        return false
    }
}