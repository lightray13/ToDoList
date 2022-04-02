package com.todo.list.data.repository.editTask

import androidx.lifecycle.LiveData
import com.todo.list.data.Result
import com.todo.list.data.database.model.TaskEntity
import com.todo.list.data.database.model.TaskListEntity
import javax.inject.Inject

class EditTaskRepository @Inject constructor(private val editTaskDataSource: EditTaskDataSource) {
    val allTaskLists: LiveData<List<TaskListEntity>> = editTaskDataSource.allTasksLists

    fun taskById(id: Long) = editTaskDataSource.taskById(id)

    suspend fun updateTaskIntoDatabase(taskEntity: TaskEntity): Result<String> {
        val result = editTaskDataSource.updateTask(taskEntity)
        return if (result) Result.Success("Task updated")
        else Result.Error("Task not updated")
    }

    suspend fun deleteTaskFromDatabase(id: Long): Result<String> {
        val result = editTaskDataSource.deleteTask(id)
        return if (result) Result.Success("Task deleted")
        else Result.Error("Task not deleted")
    }

    suspend fun insertTaskListIntoDatabase(taskList: TaskListEntity): Result<String> {
        val result = editTaskDataSource.insertTasksListIntoDatabase(taskList)
        return if (result) Result.Success("Task list added")
        else Result.Error("Task list not added")
    }
}