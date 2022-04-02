package com.todo.list.data

sealed class Result<out R> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val message: String): Result<Nothing>()

    override fun toString(): String {
        return when(this) {
            is Success<*> -> "Success [data = $data]"
            is Error -> "Error [message = $message]"
        }
    }
}

val Result<*>.successed
    get() = this is Result.Success && data != null