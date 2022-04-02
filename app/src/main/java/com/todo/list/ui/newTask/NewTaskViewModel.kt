package com.todo.list.ui.newTask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.todo.list.data.Result
import androidx.lifecycle.viewModelScope
import com.todo.list.data.database.model.TaskEntity
import com.todo.list.data.database.model.TaskListEntity
import com.todo.list.data.repository.newTask.NewTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewTaskViewModel @Inject constructor(private val repository: NewTaskRepository): ViewModel() {
    val allTaskLists: LiveData<List<TaskListEntity>> = repository.allTaskLists

    private val _toastError = MutableLiveData<String>()
    val toastError: LiveData<String> = _toastError

    private val _toastSuccess = MutableLiveData<String?>()
    val toastSuccess: LiveData<String?> = _toastSuccess

    private val _toastSuccessList = MutableLiveData<String?>()
    val toastSuccessList: LiveData<String?> = _toastSuccessList

    fun insertTask(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.insertTaskIntoDatabase(task)
            when(result) {
                is Result.Success -> _toastSuccess.postValue(result.data)
                is Result.Error -> _toastError.postValue(result.message)
            }
        }
    }

    fun insertTaskList(taskList: TaskListEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.insertTaskListIntoDatabase(taskList)
            when(result) {
                is Result.Success -> _toastSuccessList.postValue(result.data)
                is Result.Error -> _toastError.postValue(result.message)
            }
        }
    }
}