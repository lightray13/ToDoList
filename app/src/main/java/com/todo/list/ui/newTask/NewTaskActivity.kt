package com.todo.list.ui.newTask

import android.app.AlertDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.todo.list.R
import com.todo.list.common.BaseActivity
import com.todo.list.data.database.model.TaskEntity
import com.todo.list.data.database.model.TaskListEntity
import com.todo.list.databinding.ActivityNewTaskBinding
import com.todo.list.util.DataTimeHelper
import com.todo.list.util.SpinnerHelper
import com.todo.list.util.doOnChange
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_new_task.*
import kotlinx.android.synthetic.main.activity_new_task.toolbar
import java.util.*

@AndroidEntryPoint
class NewTaskActivity : BaseActivity() {
    private val viewModel: NewTaskViewModel by viewModels()

    private lateinit var binding: ActivityNewTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_task)
        binding.apply {
            lifecycleOwner = this@NewTaskActivity
            viewModel = this@NewTaskActivity.viewModel
        }

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        observeViewModel()
        SpinnerHelper.displayPriorityList(this@NewTaskActivity, prioritySpinner)

        ivDate.setOnClickListener(clickListener)
        ivTime.setOnClickListener(clickListener)
        fabAdd.setOnClickListener(clickListener)
        ivAddTask.setOnClickListener(clickListener)
    }

    override fun observeViewModel() {
        viewModel.allTaskLists.doOnChange(this) {
            SpinnerHelper.displayTaskLists(this@NewTaskActivity, taskListsSpinner, it)
        }

        viewModel.toastSuccess.doOnChange(this) {
            showMessage(it.toString())
            finish()
        }

        viewModel.toastSuccessList.doOnChange(this) {
            showMessage(it.toString())
        }

        viewModel.toastError.doOnChange(this) {
            showError(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private val clickListener = View.OnClickListener {view ->
        when (view.id) {
            R.id.ivDate -> DataTimeHelper.showDatePicker(etDate)
            R.id.ivTime -> DataTimeHelper.showTimePicker(etTime)
            R.id.fabAdd -> insertTask()
            R.id.ivAddTask -> showDialog()
        }
    }

    private fun insertTask() {
        if (!etTask.text.isNullOrEmpty()) {
            val taskEntity = TaskEntity(0, etTask.text.toString(), etDate.text.toString(), etTime.text.toString(),
                taskListsSpinner.text.toString(), taskListsSpinner.selectedIndex, prioritySpinner.selectedIndex, false)
            viewModel.insertTask(taskEntity)
        } else {
            showError("Enter task!")
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.edit_text_layout, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.edit_text)

        with(builder) {
            setTitle("Enter a name for the new list")
            setPositiveButton("OK") { dialog, which ->
                val text = editText.text.toString()
                if (!text.isNullOrEmpty()) {
                    val taskList = TaskListEntity(text)
                    viewModel.insertTaskList(taskList)
                }
            }
            setNegativeButton("Cancel") { dialog, which ->

            }
            setView(dialogLayout)
            show()
        }
    }
}