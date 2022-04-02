package com.todo.list.data.repository.tasksList

import androidx.lifecycle.LiveData
import com.todo.list.data.Result
import com.todo.list.data.database.model.TaskEntity
import com.todo.list.data.database.model.TaskListEntity
import com.todo.list.data.preferences.SharedPreferenceStorage
import javax.inject.Inject

class TaskListRepository @Inject constructor(
    private val tasksListDataSource: TasksListDataSource,
    private val preferenceStorage: SharedPreferenceStorage
) {
    val allTasks: LiveData<List<TaskEntity>> = tasksListDataSource.allTasks

    suspend fun defaultTasksList(list: List<TaskListEntity>) {
        if (addDefaultLists()) {
            tasksListDataSource.insertDefaultListNamesIntoDatabase(list)
            preferenceStorage.isFirstLoginAt = false
        }
    }

    suspend fun updateCompletedStatus(id: Long): Result<TaskEntity> {
        val result = tasksListDataSource.updateCompletedStatus(id)
        return result?.let {
            Result.Success(it)
        } ?: Result.Error("Task completed!")
    }

    private fun addDefaultLists(): Boolean {
        return preferenceStorage.isFirstLoginAt
    }
}