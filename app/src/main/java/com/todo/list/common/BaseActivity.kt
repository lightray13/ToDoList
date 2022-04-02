package com.todo.list.common

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

interface InitViews{
    fun observeViewModel()
}

interface ShowToast {
    fun showMessage(message: String)

    fun showError(error: String)
}

abstract class BaseActivity: AppCompatActivity(), InitViews, ShowToast {

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}