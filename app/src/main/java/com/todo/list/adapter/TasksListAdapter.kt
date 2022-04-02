package com.todo.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.todo.list.R
import com.todo.list.data.database.model.TaskEntity
import com.todo.list.util.DataTimeHelper
import com.todo.list.util.emptyIfNull
import kotlinx.android.synthetic.main.item_task_list.view.*

interface OnItemClickCallback {

    fun onItemCallback(id: Long)

    fun onCompletedClick(id: Long)
}
class TasksListAdapter(private val onItemClickCallback: OnItemClickCallback): RecyclerView.Adapter<TasksListAdapter.TasksListViewHolder>() {
    private val tasksList = mutableListOf<TaskEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksListViewHolder {
        return TasksListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_task_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TasksListViewHolder, position: Int) {
        holder.bind(tasksList[position], onItemClickCallback)
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    fun updateList(list: List<TaskEntity>) {
        this.tasksList.clear()
        this.tasksList.addAll(list)
        notifyDataSetChanged()
    }

    class TasksListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(model: TaskEntity, onItemClickCallback: OnItemClickCallback) {
            itemView.taskItemNameTextView.text = model.name.emptyIfNull()
            itemView.taskItemListTextView.text = model.listName.emptyIfNull()

            DataTimeHelper.showDataTimeByPriority(itemView.taskItemDateTextView,
            model.date, model.time, model.priority)

            itemView.taskItemImageView.isChecked = model.isCompleted

            itemView.taskItemImageView.setOnClickListener {
                onItemClickCallback.onCompletedClick(model.id)
            }

            itemView.setOnClickListener {
                onItemClickCallback.onItemCallback(model.id)
            }
        }
    }
}