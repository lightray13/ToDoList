package com.todo.list.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.todo.list.R
import java.text.SimpleDateFormat
import java.util.*

object DataTimeHelper {
    private var cal = Calendar.getInstance()

    fun showDatePicker(editText: EditText) {
        DatePickerDialog(editText.context,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val time = cal.time

                val dateFormat = SimpleDateFormat("E, dd MMMM yyyy")
                val date = dateFormat.format(time)
                editText.setText(date)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    fun showTimePicker(editText: EditText) {
        TimePickerDialog(editText.context,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val time = String.format("%d : %d", hourOfDay, minute)
                editText.setText(time)
            },
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }

    fun showDataTimeByPriority(textView: TextView, data: String?, time: String?, priority: Int? = 0) {
        if (!data.isNullOrEmpty() && !time.isNullOrEmpty()) {
            textView.text = data.plus(", ").plus(time)
        } else if (!data.isNullOrEmpty()) {
            textView.text = data
        } else if (!time.isNullOrEmpty()) {
            textView.text = time
        }

        val context = textView.context

        when(priority) {
            Constants.LOW_PRIORITY ->  textView.setTextColor(ContextCompat.getColor(context, R.color.blue))
            Constants.MEDIUM_PRIORITY -> textView.setTextColor(ContextCompat.getColor(context, R.color.green))
            Constants.HIGH_PRIORITY -> textView.setTextColor(ContextCompat.getColor(context, R.color.yellow))
            Constants.CRITICAL_PRIORITY -> textView.setTextColor(ContextCompat.getColor(context, R.color.red))
        }
    }
}