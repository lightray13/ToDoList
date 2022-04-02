package com.todo.list.data.repository.tasksList

import androidx.lifecycle.LiveData
import com.todo.list.data.database.TaskDatabase
import com.todo.list.data.database.model.TaskEntity
import com.todo.list.data.database.model.TaskListEntity
import javax.inject.Inject

class TasksListDataSource @Inject constructor(private val database: TaskDatabase) {
    val allTasks: LiveData<List<TaskEntity>> = database.taskDao().taskList()

    suspend fun updateCompletedStatus(id: Long): TaskEntity? {
        val task = database.taskDao().taskFromId(id)
        task?.let {
           val taskEntity = TaskEntity(
               it.id,
               it.name,
               it.date,
               it.time,
               it.listName,
               it.listPosition,
               it.priority,
               it.isCompleted.not()
           )
            if (database.taskDao().updateTaskListEntity(taskEntity) > 0) {
                return taskEntity
            }
        }
        return null;
    }

    suspend fun insertDefaultListNamesIntoDatabase(tasksToInsert: List<TaskListEntity>) {
        if (tasksToInsert.isNotEmpty()) {
            database.taskListDao().insertTasksList(tasksToInsert)
        }
    }

}