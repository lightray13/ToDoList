package com.todo.list.util

fun String?.emptyIfNull(): String {
    return this ?: ""
}