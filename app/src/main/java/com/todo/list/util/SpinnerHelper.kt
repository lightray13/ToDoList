package com.todo.list.util

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.skydoves.powerspinner.IconSpinnerAdapter
import com.skydoves.powerspinner.IconSpinnerItem
import com.skydoves.powerspinner.PowerSpinnerView
import com.todo.list.R
import com.todo.list.data.database.model.TaskListEntity

object SpinnerHelper {
    fun displayTaskLists(mLifecycleOwner: LifecycleOwner, view: View, taskLists: List<TaskListEntity>, index: Int = 0) {
        (view as? PowerSpinnerView)?.apply {
            setSpinnerAdapter(IconSpinnerAdapter(this))
            val list: MutableList<IconSpinnerItem> = mutableListOf()
            for (element in taskLists) {
                when(element.listName) {
                    Constants.DEFAULT_PERSONAL_LIST -> list.add(IconSpinnerItem(text = element.listName, iconRes = R.drawable.baseline_person))
                    Constants.DEFAULT_WORK_LIST -> list.add(IconSpinnerItem(text = element.listName, iconRes = R.drawable.baseline_work))
                    Constants.DEFAULT_PURCHASES_LIST -> list.add(IconSpinnerItem(text = element.listName, iconRes = R.drawable.baseline_shop))
                    else -> list.add(IconSpinnerItem(text = element.listName, iconRes = R.drawable.baseline_list))
                }
            }
            setItems(list)
            getSpinnerRecyclerView().layoutManager = LinearLayoutManager(context)
            lifecycleOwner = mLifecycleOwner
            selectItemByIndex(index)
        }
    }

    fun displayPriorityList(mLifecycleOwner: LifecycleOwner, view: View, index: Int = 3) {
        (view as? PowerSpinnerView)?.apply {
            setSpinnerAdapter(IconSpinnerAdapter(this))
            setItems(arrayListOf(
                IconSpinnerItem(text = Constants.CRITICAL_PRIORITY_STRING, iconRes = R.drawable.baseline_circle_critical),
                IconSpinnerItem(text = Constants.HIGH_PRIORITY_STRING, iconRes = R.drawable.baseline_arrow_circle_up),
                IconSpinnerItem(text = Constants.MEDIUM_PRIORITY_STRING, iconRes = R.drawable.baseline_circle_medium),
                IconSpinnerItem(text = Constants.LOW_PRIORITY_STRING, iconRes = R.drawable.baseline_arrow_circle_down)))
            getSpinnerRecyclerView().layoutManager = GridLayoutManager(context, 2)
            lifecycleOwner = mLifecycleOwner
            selectItemByIndex(index)
        }
    }
}