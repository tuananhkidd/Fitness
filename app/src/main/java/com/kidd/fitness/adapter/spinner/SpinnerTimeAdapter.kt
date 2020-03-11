package com.kidd.fitness.adapter.spinner

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.kidd.fitness.R
import com.kidd.fitness.entity.DailyTime
import com.kidd.fitness.extension.inflate

class SpinnerTimeAdapter() : BaseAdapter() {
    private val listDailyTime = DailyTime.values()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: TextView = p2?.inflate(R.layout.item_spinner)!! as TextView
        view.text = listDailyTime[p0].name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: TextView = parent?.inflate(R.layout.item_spinner)!! as TextView
        view.text = listDailyTime[position].name
        return view
    }

    override fun getItem(p0: Int): DailyTime = listDailyTime[p0]

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount() = listDailyTime.size
}