package com.todo.list.ui.tasksList

import androidx.lifecycle.*
import com.todo.list.data.Result
import com.todo.list.data.database.model.TaskEntity
import com.todo.list.data.database.model.TaskListEntity
import com.todo.list.data.repository.tasksList.TaskListRepository
import com.todo.list.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksListViewModel @Inject constructor(private val repository: TaskListRepository): ViewModel() {

    private val _toastError = MutableLiveData<String>()
    val toastError: LiveData<String> = _toastError

    private val _completedStock = MutableLiveData<TaskEntity?>()
    val completedStock: LiveData<TaskEntity?> = _completedStock

    val allTasks: LiveData<List<TaskEntity>> = repository.allTasks

    private val _allTasksEmpty = MutableLiveData<Boolean>()
    val allTasksEmpty: LiveData<Boolean> = _allTasksEmpty

    fun updateCompletedStatus(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.updateCompletedStatus(id)
            when(result) {
                is Result.Success -> _completedStock.postValue(result.data)
                is Result.Error -> _toastError.postValue(result.message)
            }
        }
    }

    fun addDefaultLists() {
        viewModelScope.launch(Dispatchers.IO) {
            val list: List<TaskListEntity> = listOf(
                TaskListEntity(Constants.DEFAULT_ALL_LIST),
                TaskListEntity(Constants.DEFAULT_COMPLETED_LIST),
                TaskListEntity(Constants.BY_DEFAULT_LIST),
                TaskListEntity(Constants.DEFAULT_PERSONAL_LIST),
                TaskListEntity(Constants.DEFAULT_WORK_LIST),
                TaskListEntity(Constants.DEFAULT_PURCHASES_LIST),
            )
            repository.defaultTasksList(list)
        }
    }

    fun isAllTasksEmpty(empty: Boolean) {
        _allTasksEmpty.postValue(empty)
    }
}