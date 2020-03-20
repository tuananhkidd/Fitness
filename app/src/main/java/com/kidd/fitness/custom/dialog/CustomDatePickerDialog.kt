package com.kidd.fitness.custom.dialog

import android.widget.DatePicker
import android.app.DatePickerDialog
import android.content.Context
import com.kidd.fitness.R
import java.util.*

class CustomDatePickerDialog(context: Context, listener: OnSelectDateListener) :
    DatePickerDialog.OnDateSetListener {

    private var mContext = context
    private var mListener = listener

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        mListener.onSelectedDateSuccess(dayOfMonth, month + 1, year)
    }

    fun showDialogDatePicker() {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        val dialog = DatePickerDialog(
            mContext,
            R.style.DateTimePickerStyle,
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.show()
    }

    fun showDialogDatePicker(calendar: Calendar) {
        val dialog = DatePickerDialog(
            mContext,
            R.style.DateTimePickerStyle,
            this,
            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.show()
    }

    interface OnSelectDateListener {
        fun onSelectedDateSuccess(day: Int, month: Int, year: Int)
    }

    fun formatDate(value: String): String {
        return if (value.length == 1) {
            "0$value"
        } else {
            value
        }
    }

    fun getDate(day: Int, month: Int, year: Int): String {
        return formatDate(day.toString()) + "/" + formatDate(month.toString()) + "/" + formatDate(
            year.toString()
        )
    }

    fun getFormatDateApi(day: String, month: String, year: String): String {
        return if (day.isEmpty() || month.isEmpty() || year.isEmpty()) ""
        else {
            "$year-$month-$day"
        }
    }
}