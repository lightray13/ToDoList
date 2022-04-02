package com.todo.list.ui.tasksList

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.todo.list.R
import com.todo.list.adapter.OnItemClickCallback
import com.todo.list.adapter.TasksListAdapter
import com.todo.list.common.BaseActivity
import com.todo.list.data.database.model.TaskEntity
import com.todo.list.databinding.ActivityMainBinding
import com.todo.list.ui.editTask.EditTaskActivity
import com.todo.list.ui.newTask.NewTaskActivity
import com.todo.list.util.Constants
import com.todo.list.util.SpinnerHelper
import com.todo.list.util.doOnChange
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_new_task.*
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity(), OnItemClickCallback {
    private val viewModel: TasksListViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private var tasksListAdapter = TasksListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }

        setSupportActionBar(toolbar)
        viewModel.addDefaultLists()
        observeViewModel()

        fabAddTask.bringToFront()
        fabAddTask.setOnClickListener {
            startActivity(Intent(this, NewTaskActivity::class.java))
        }

        initializeViews()
    }

    private fun initializeViews() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tasksListAdapter
        }
    }

    override fun observeViewModel() {

        viewModel.allTasks.doOnChange(this) {
            tasksListAdapter.updateList(it)

            if (it.isEmpty()) viewModel.isAllTasksEmpty(true)
            else viewModel.isAllTasksEmpty(false)
        }

        viewModel.completedStock.doOnChange(this) {
            it?.let {
                if (it.isCompleted) showMessage("Task completed!")
                else showError("Task not completed")
            }
        }

        viewModel.toastError.doOnChange(this) {
            showError(it)
        }
    }

    override fun onItemCallback(id: Long) {
        val intent = Intent(this, EditTaskActivity::class.java)
        intent.putExtra(Constants.EXTRA_TASK_ID, id)
        startActivity(intent)
    }

    override fun onCompletedClick(id: Long) {
        viewModel.updateCompletedStatus(id)
    }
}