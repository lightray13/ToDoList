package com.todo.list.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.todo.list.data.database.model.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_list ORDER BY UPPER(priority)")
    fun taskList(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM task_list WHERE id = :id")
    suspend fun taskFromId(id: Long): TaskEntity?

    @Query("SELECT * FROM task_list WHERE id = :id")
    fun taskLiveDataFromId(id: Long): LiveData<TaskEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskEntity(data: TaskEntity): Long

    @Update
    suspend fun updateTaskListEntity(data: TaskEntity): Int

    @Delete
    suspend fun deleteTaskListEntity(data: TaskEntity): Int

    @Query("DELETE FROM task_list")
    suspend fun deleteAll()
}