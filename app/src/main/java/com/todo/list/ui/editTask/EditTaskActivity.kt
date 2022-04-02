package com.todo.list.ui.editTask

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.todo.list.R
import com.todo.list.common.BaseActivity
import com.todo.list.data.database.model.TaskEntity
import com.todo.list.data.database.model.TaskListEntity
import com.todo.list.databinding.ActivityEditTaskBinding
import com.todo.list.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_edit_task.*
import kotlinx.android.synthetic.main.activity_new_task.*
import kotlinx.android.synthetic.main.activity_new_task.ivDate
import kotlinx.android.synthetic.main.activity_new_task.ivTime
import kotlinx.android.synthetic.main.activity_new_task.prioritySpinner

@AndroidEntryPoint
class EditTaskActivity : BaseActivity() {
    private val viewModel: EditTaskViewModel by viewModels()

    private lateinit var binding: ActivityEditTaskBinding

    private var taskId: Long? = null
    private var listPosition: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_task)
        binding.apply {
            lifecycleOwner = this@EditTaskActivity
            viewModel = this@EditTaskActivity.viewModel
        }

        setSupportActionBar(toolbarUpdate)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (intent?.hasExtra(Constants.EXTRA_TASK_ID) == true) {
            taskId = intent?.getLongExtra(Constants.EXTRA_TASK_ID, 0)
        }

        observeViewModel()

        ivDate.setOnClickListener(clickListener)
        ivTime.setOnClickListener(clickListener)
        fabUpdateTask.setOnClickListener(clickListener)
        ivAddTasks.setOnClickListener(clickListener)
    }

    private fun initializeViews(task: TaskEntity) {
        etTaskUpdate.setText(task.name)
        etDateUpdate.setText(task.date.emptyIfNull())
        etTimeUpdate.setText(task.time.emptyIfNull())
        SpinnerHelper.displayPriorityList(this@EditTaskActivity, prioritySpinner, task.priority ?: 0)
        listPosition = task.listPosition
        completedImage.isChecked = task.isCompleted
    }

    override fun observeViewModel() {

        taskId?.let {
                viewModel.taskById(it).doOnChange(this) { task ->
                    task?.let {
                        initializeViews(task)
                    }
                }
        }

        viewModel.allTaskLists.doOnChange(this) {
            SpinnerHelper.displayTaskLists(this@EditTaskActivity, listSpinner,  it, listPosition ?: 0)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.edit_task_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.action_delete -> viewModel.deleteTask(taskId ?: 0)
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.ivDate -> DataTimeHelper.showDatePicker(etDateUpdate)
            R.id.ivTime -> DataTimeHelper.showTimePicker(etTimeUpdate)
            R.id.fabUpdateTask -> updateTask()
            R.id.ivAddTask -> showDialog()
        }
    }

    private fun updateTask() {
        taskId?.let {
            val taskEntity = TaskEntity(
                it,
                etTaskUpdate.text.toString(),
                etDateUpdate.text.toString(),
                etTimeUpdate.text.toString(),
                listSpinner.text.toString(),
                listSpinner.selectedIndex,
                prioritySpinner.selectedIndex,
                completedImage.isChecked
            )
            viewModel.updateTask(taskEntity)
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