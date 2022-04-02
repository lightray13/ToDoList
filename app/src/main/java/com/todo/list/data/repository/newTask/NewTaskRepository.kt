package com.todo.list.data.repository.newTask

import androidx.lifecycle.LiveData
import com.todo.list.data.database.model.TaskEntity
import com.todo.list.data.Result
import com.todo.list.data.database.model.TaskListEntity
import javax.inject.Inject

class NewTaskRepository @Inject constructor(private val newTaskDataSource: NewTaskDataSource) {
    val allTaskLists: LiveData<List<TaskListEntity>> = newTaskDataSource.allTasksLists

    suspend fun insertTaskIntoDatabase(task: TaskEntity): Result<String> {
        val result = newTaskDataSource.insertTaskIntoDatabase(task)
        return if (result) Result.Success("Task added")
        else Result.Error("Task not added")
    }

    suspend fun insertTaskListIntoDatabase(taskList: TaskListEntity): Result<String> {
        val result = newTaskDataSource.insertTasksListIntoDatabase(taskList)
        return if (result) Result.Success("Task list added")
        else Result.Error("Task list not added")
    }
}