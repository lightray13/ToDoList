package com.todo.list.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.todo.list.data.database.model.TaskListEntity

@Dao
interface TaskListDao {

    @Query("SELECT * FROM list")
    fun taskList(): LiveData<List<TaskListEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListTaskEntity(data: TaskListEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasksList(list: List<TaskListEntity>)
}