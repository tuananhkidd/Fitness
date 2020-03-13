package com.kidd.fitness.adapter.spinner

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.kidd.fitness.R
import com.kidd.fitness.entity.Food
import com.kidd.fitness.extension.inflate

class SpinnerFoodAdapter(val listFood:List<Food>) : BaseAdapter() {
    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        val view: TextView = p2?.inflate(R.layout.item_spinner)!! as TextView
        view.text = listFood[position].name + " (${listFood[position].calo} kcal/g)"
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: TextView = parent?.inflate(R.layout.item_spinner)!! as TextView
        view.text = listFood[position].name + " (${listFood[position].calo} kcal/g)"
        return view
    }

    override fun getItem(p0: Int): Food {
        return listFood[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return listFood.size
    }
}